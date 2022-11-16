package com.dfx.reverseproxy.settings.userprovison.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dfx.reverseproxy.settings.userprovison.model.TBL_DMS_USERS;
import com.dfx.reverseproxy.settings.userprovison.repository.TBL_DFX_GroupsRepository;
import com.dfx.reverseproxy.settings.userprovison.repository.TBL_DFX_UserGroupsRepository;
import com.dfx.reverseproxy.settings.userprovison.repository.UsersRepository;


@SpringBootTest()
@AutoConfigureMockMvc
@Sql("/ntlm-user/Users.sql")
class UserAuthorizationControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	TBL_DFX_GroupsRepository dfxGroupsRepository;
	
	@Autowired
	TBL_DFX_UserGroupsRepository dfxUserGroupsRepository;
	
	@Test
	@WithMockUser(username = "dfxadmin", password = "password", roles = "USER")
	void testCreateGroup() throws Exception {
		String json="{\"groupName\":\"LON\",\"groupAccessRights\":\"Read\"}";
		String uri="/api/dms/userprovision/group";
		MvcResult mvcResult;
		mvcResult = mvc
				.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		checkExistingGroup();
		dfxGroupsRepository.deleteAll();
	}

	private void checkExistingGroup() throws Exception {
		String json="{\"groupName\":\"LON\",\"groupAccessRights\":\"Read\"}";
		String uri="/api/dms/userprovision/group";
		MvcResult mvcResult;
		mvcResult = mvc
				.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		
	}

	@Test
	@WithMockUser(username = "dfxadmin", password = "password", roles = "USER")
	void testCreateUserGroup() throws Exception {
		dfxUserGroupsRepository.deleteAll();
		String json = "{\"groupNames\": [\"LON\" ], \"logonId\": \"dfxadmin\"}";

		String uri="/api/dms/userprovision/user";
		MvcResult mvcResult;
		mvcResult = mvc
				.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		checkExistingUserGroup();
		testUnavailableLogonId(uri);
		
	}

	private void checkExistingUserGroup() throws Exception {
		String json="{\"groupNames\": [\r\n" + 
				"    \"LON\"\r\n" + 
				"  ],\r\n" + 
				"  \"logonId\": \"string\"\r\n" + 
				"}";

		String uri="/api/dms/userprovision/user";
		MvcResult mvcResult;
		mvcResult = mvc
				.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		
	}

	private void testUnavailableLogonId(String uri) throws Exception {
		String json="{\"groupNames\": [\r\n" + 
				"    \"LON\"\r\n" + 
				"  ],\r\n" + 
				"  \"logonId\": \"string\"\r\n" + 
				"}";
		
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		dfxUserGroupsRepository.deleteAll();
	}
	
	
	@Test
	@WithMockUser(username = "dfxadmin", password = "password", roles = "USER")
	void retrieveUsers() throws Exception{
		String uri = "/api/dms/userprovision/users";
		String json="{\"filtervalue\":\"dfx\"\r\n" + 
				"}";
		
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		boolean res = mvcResult.getResponse().getContentAsString().contains("dfxadmin");

		assertEquals(true, res);
		assertEquals(200, status);
	}
	
	@Test
	@WithMockUser(username = "dfxadmin", password = "password", roles = "USER")
	void retrieveGroups() throws Exception{
		createGroup();	
		String groupuri = "/api/dms/userprovision/group/all";
		MvcResult mvcResultCI = mvc.perform(MockMvcRequestBuilders.get(groupuri)).andReturn();
		int groupstatus = mvcResultCI.getResponse().getStatus();
		boolean res = mvcResultCI.getResponse().getContentAsString().contains("LON");
	
		assertEquals(true, res);
		assertEquals(200, groupstatus);
		
		dfxGroupsRepository.deleteAll();	
		checkForEmptyGroup();
	}
	
	private void checkForEmptyGroup() throws Exception {
		String groupuri = "/api/dms/userprovision/group/all";
		MvcResult mvcResultCI = mvc.perform(MockMvcRequestBuilders.get(groupuri)).andReturn();
		int groupstatus = mvcResultCI.getResponse().getStatus();
		assertEquals(400, groupstatus);
	}

	@Test
	@WithMockUser(username = "dfxadmin", password = "password", roles = "USER")
	void retrieveUserForGroup() throws Exception{
		clearData();	
		createUserGroup();	
		createGroup();
		String userUri="/api/dms/userprovision/group/LON";
		MvcResult mvcResultUser = mvc.perform(MockMvcRequestBuilders.get(userUri)).andReturn();
		int userstatus = mvcResultUser.getResponse().getStatus();
		boolean res = mvcResultUser.getResponse().getContentAsString().contains("dfxadmin");
		assertEquals(true, res);
		assertEquals(200, userstatus);
		clearData();
		checkEmptyGroup();
	}
	
	private void checkEmptyGroup() throws Exception {
		String userUri="/api/dms/userprovision/group/LON";
		MvcResult mvcResultUser = mvc.perform(MockMvcRequestBuilders.get(userUri)).andReturn();
		int userstatus = mvcResultUser.getResponse().getStatus();	
		assertEquals(400, userstatus);
	}
	
	void clearData() {
		dfxUserGroupsRepository.deleteAll();
		dfxGroupsRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "dfxadmin", password = "password", roles = "USER")
	void retrieveGroupForUser() throws Exception {
		dfxUserGroupsRepository.deleteAll();
		createUserGroup();
		String userUri="/api/dms/userprovision/user";
		String json="{\"logonId\":\"dfxadmin\"\r\n" + 
				"}";
		
		MvcResult mvcResultUser = mvc
				.perform(MockMvcRequestBuilders.post(userUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();
		int userstatus = mvcResultUser.getResponse().getStatus();
		boolean res = mvcResultUser.getResponse().getContentAsString().contains("LON");
		assertEquals(true, res);
		assertEquals(200, userstatus);
		dfxUserGroupsRepository.deleteAll();
	}

	void createUserGroup() throws Exception {
		String json="{\"groupNames\": [\"LON\"],\"logonId\": \"dfxadmin\"}";
		String uri="/api/dms/userprovision/user";
		MvcResult mvcResult;
		mvcResult = mvc
				.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}
	
	void createGroup() throws Exception {
		String json="{\"groupName\":\"LON\"}";
		String uri="/api/dms/userprovision/group";
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}
}
