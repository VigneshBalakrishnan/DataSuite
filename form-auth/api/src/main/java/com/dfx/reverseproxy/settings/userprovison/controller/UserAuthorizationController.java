package com.dfx.reverseproxy.settings.userprovison.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dfx.reverseproxy.settings.userprovison.bean.DFXUserGroups;
import com.dfx.reverseproxy.settings.userprovison.model.TBL_DFX_Groups;
import com.dfx.reverseproxy.settings.userprovison.model.TBL_DFX_User_Groups;
import com.dfx.reverseproxy.settings.userprovison.model.TBL_DMS_Groups;
import com.dfx.reverseproxy.settings.userprovison.model.TBL_DMS_User_Groups;
import com.dfx.reverseproxy.settings.userprovison.repository.GroupsRepository;
import com.dfx.reverseproxy.settings.userprovison.repository.TBL_DFX_GroupsRepository;
import com.dfx.reverseproxy.settings.userprovison.repository.TBL_DFX_UserGroupsRepository;
import com.dfx.reverseproxy.settings.userprovison.repository.UserGroupsRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@PropertySource("classpath:userprovision.properties")
@RequestMapping("/api/dms/userprovision")
public class UserAuthorizationController {
	
	@Value("#{'${userprovision.users}'.split(',')}")
	private List<String> username;
	
	@Autowired
	GroupsRepository groupsRepository;
	
	@Autowired
	UserGroupsRepository userGroupsRepository;
	
	@Autowired
	TBL_DFX_GroupsRepository dfxGroupsRepository;
	
	@Autowired
	TBL_DFX_UserGroupsRepository dfxUserGroupsRepository;
	
	Logger log = LoggerFactory.getLogger(UserAuthorizationController.class);
	
	@ApiOperation(notes = "Add new groups", value = "add groups", response = UserAuthorizationController.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@RequestMapping(method = {RequestMethod.PUT }, value = "/group",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createGroup(@RequestBody TBL_DFX_Groups groups) {
		try {
			
			TBL_DFX_Groups checkforDFXGroup= dfxGroupsRepository.findByGroupName(groups.getGroupName());
			TBL_DMS_Groups checkforDMSGroup=groupsRepository.findByGroupName(groups.getGroupName());
			String status;
			HttpStatus returnStatus=HttpStatus.CREATED;
			if(checkforDFXGroup==null && checkforDMSGroup==null) { 
				groups.setDateCreated(new Date());
				dfxGroupsRepository.save(groups);
				status="New group created";
			}else {
				returnStatus=HttpStatus.BAD_REQUEST;
				status="Group already existing";
			}		
			return ResponseEntity.status(returnStatus).body(JSONmessage(status));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONmessage(e.getMessage()));
		}
	}
	
	@ApiOperation(notes = "Assign group for the user", value = "assign group to user", response = UserAuthorizationController.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@RequestMapping(method = {RequestMethod.PUT }, value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUserGroup(@RequestBody DFXUserGroups userGroups) {
		try {
			
			String logonId=userGroups.getLogonId();
			List<String> newGroupNames=userGroups.getGroupNames();
			List<String> existingGroupNames=new ArrayList<String>();
			List<String> existingdfxGroupNames=new ArrayList<String>();
			String status = "Success";
			HttpStatus returnStatus=HttpStatus.CREATED;
		
			if(username.contains(logonId)) {
				List<TBL_DMS_User_Groups> existingUserGroups=userGroupsRepository.findByLogonId(logonId);
				List<TBL_DFX_User_Groups> existingDfxUserGroups=dfxUserGroupsRepository.findByLogonIdAndDeletedRecord(logonId,false);
				
				existingUserGroups.forEach((existingUserGroup)->{
					existingGroupNames.add(existingUserGroup.getGroupName());
				});
				
				existingDfxUserGroups.forEach((existingDfxUserGroup)->{
					existingGroupNames.add(existingDfxUserGroup.getGroupName());
					existingdfxGroupNames.add(existingDfxUserGroup.getGroupName());
				});
				
				existingdfxGroupNames.stream().filter( groupName -> !newGroupNames.contains(groupName) ).forEach(groupName -> {
					deleteUsergroup(logonId, groupName);		
				});
				
				newGroupNames.stream().filter( groupName -> !existingGroupNames.contains(groupName) ).forEach(groupName -> {
					saveUsergroup(groupName,logonId);	
				});
				
				Collections.sort(existingGroupNames);
				Collections.sort(newGroupNames);
				if(existingGroupNames.equals(newGroupNames)) {
					status="Group assigned to the user already";
					returnStatus=HttpStatus.BAD_REQUEST;
				}
		
			}else {
				status="User's logonid does not exist";
				returnStatus=HttpStatus.BAD_REQUEST;
			}

			return ResponseEntity.status(returnStatus).body(JSONmessage(status));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONmessage(e.getMessage()));
		}
	}
	
	private void deleteUsergroup(String logonId, String groupName) {
		TBL_DFX_User_Groups dfxUserGroups= dfxUserGroupsRepository.findByLogonIdAndGroupNameAndDeletedRecord(logonId, groupName,false);
		dfxUserGroups.setDeletedRecord(true);
		dfxUserGroups.setDateModified(new Date());
		dfxUserGroupsRepository.save(dfxUserGroups);
	}

	private void saveUsergroup(String groupName, String logonId) {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
		String datetime = ft.format(dNow);
		TBL_DFX_User_Groups userGroup = new TBL_DFX_User_Groups();
		userGroup.setUserGroupId(Long.parseLong(datetime));
		userGroup.setDateCreated(new Date());
		userGroup.setGroupName(groupName);
		userGroup.setLogonId(logonId);
		dfxUserGroupsRepository.save(userGroup);
	}

	@ApiOperation(notes = "Retrieve the users by the search filter of logonid", value = "get users", response = UserAuthorizationController.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@RequestMapping(method = {RequestMethod.GET }, value = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveUsers() {
		try {
			JSONArray userLists = new JSONArray();
			for(int i=0;i<username.size();i++) {
				userLists.put(username.get(i));
			}
			JSONObject userList = new JSONObject();
			userList.put("users", userLists);
		return ResponseEntity.status(HttpStatus.OK).body(userList.toString());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONmessage(e.getMessage()));
		}
	}
	

	@ApiOperation(notes = "Retrieve the groups", value = "get all groups", response = UserAuthorizationController.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@RequestMapping(method = {RequestMethod.GET }, value = "/group/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveGroups() {
		try {
		List<TBL_DMS_Groups> groups=groupsRepository.findAll();
		List<TBL_DFX_Groups> alldfxGroups= dfxGroupsRepository.findAll();
		if(groups.size()==0 && alldfxGroups.size()==0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONmessage("No group found"));
		}
		
		ArrayList<String> listCode = new ArrayList<String>();
		List<String> dmsGroups,dfxGroups;
		
		for(TBL_DMS_Groups group:groups) {
			dmsGroups=Arrays.asList(group.getGroupName());
			listCode.addAll(dmsGroups);
		}
		
		for(TBL_DFX_Groups dfx_group:alldfxGroups) {
			dfxGroups=Arrays.asList(dfx_group.getGroupName());
			listCode.addAll(dfxGroups);
		}
		Set<String> uniqueGroups = new HashSet<String>(listCode);
		
		JSONObject roles = new JSONObject();
		roles.put("groups", uniqueGroups);
		return ResponseEntity.status(HttpStatus.OK).body(roles.toString());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONmessage(e.getMessage()));
		}
	}
	
	@ApiOperation(notes = "Retrieve the users who are under the particular group", value = "retrieve user for group", response = UserAuthorizationController.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@RequestMapping(method = {RequestMethod.GET }, value = "/group/{groupName}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveUserForGroup(@PathVariable("groupName") String groupName) {
		try {
			TBL_DFX_Groups dfxGroup= dfxGroupsRepository.findByGroupName(groupName);
			TBL_DMS_Groups groups=groupsRepository.findByGroupName(groupName);
			
			if(dfxGroup!=null || groups!=null) {	
				List<TBL_DMS_User_Groups> dmsUserGroups= userGroupsRepository.findByGroupName(groupName);
				List<TBL_DFX_User_Groups> dfxUserGroups= dfxUserGroupsRepository.findByGroupNameAndDeletedRecord(groupName,false);
				
				if(dmsUserGroups.isEmpty()&&dfxUserGroups.isEmpty()) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONmessage("User not found"));
				}
				
				ArrayList<String> listCode = new ArrayList<String>();
				List<String> dmsUsers,dfxUsers;
				
				for(TBL_DMS_User_Groups dmsUserGroup:dmsUserGroups) {
					dmsUsers= Arrays.asList(dmsUserGroup.getLogonId());
					listCode.addAll(dmsUsers);
				}
				
				for(TBL_DFX_User_Groups dfxUserGroup:dfxUserGroups) {
					dfxUsers= Arrays.asList(dfxUserGroup.getLogonId());
					listCode.addAll(dfxUsers);
				}
				Set<String> uniqueUsers = new HashSet<String>(listCode);
				
				JSONObject users = new JSONObject();
				users.put("users", uniqueUsers);
				return ResponseEntity.status(HttpStatus.OK).body(users.toString());
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONmessage("Invalid group name"));
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONmessage(e.getMessage()));
		}
		
	}
	
	@ApiOperation(notes = "Retrieve the group of the particular user", value = "retrieve groups for user", response = UserAuthorizationController.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@RequestMapping(method = {RequestMethod.POST }, value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveGroupsForUser(@RequestBody() Map<String, String> Payload) {
		try {
		String logonId=Payload.get("logonId");
		List<TBL_DMS_User_Groups> dmsUserGroups= userGroupsRepository.findByLogonId(logonId);
		List<TBL_DFX_User_Groups> dfxUserGroups= dfxUserGroupsRepository.findByLogonIdAndDeletedRecord(logonId,false);
		
		ArrayList<String> listCode = new ArrayList<String>();
		List<String> dmsUsers,dfxUsers;
		
		for(TBL_DMS_User_Groups dmsUserGroup:dmsUserGroups) {
			dmsUsers= Arrays.asList(dmsUserGroup.getGroupName());
			listCode.addAll(dmsUsers);
		}
		
		for(TBL_DFX_User_Groups dfxUserGroup:dfxUserGroups) {
			dfxUsers= Arrays.asList(dfxUserGroup.getGroupName());
			listCode.addAll(dfxUsers);
		}
		Set<String> uniqueGroups = new HashSet<String>(listCode);
		
		JSONObject groups = new JSONObject();
		groups.put("groups", uniqueGroups);
		
		return ResponseEntity.status(HttpStatus.OK).body(groups.toString());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONmessage(e.getMessage()));
		}
		}
	
	private static String JSONmessage(String message) {
	    return "{\"message\" : \"" + message + "\"}";
	   }
}
