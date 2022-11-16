package com.dfx.reverseproxy.security.ntlm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dfx.reverseproxy.security.userauthorization.UserAuthorization;
import com.dfx.reverseproxy.security.userauthorizationException.UserauthorizationException;
import com.dfx.reverseproxy.security.userauthorizationException.UserauthorizationExceptionCode;

@Component
public class RolesManager {
	
	public static String DATABASE_URL;
	public static String DATABASE_USERNAME;
	public static String DATABASE_PASSWORD;
	
	@Value("${spring.datasource.url}")
	public void setDatabaseURL(String dbURL) {
	    DATABASE_URL = dbURL;
	}
	
	@Value("${spring.datasource.username}")
	public void setDatabaseUserName(String dbUserName) {
		DATABASE_USERNAME = dbUserName;
	}
	
	@Value("${spring.datasource.password}")
	public void setDatabasePassword(String dbPassword) {
		DATABASE_PASSWORD = dbPassword;
	}

	public static List<String> getRoles(String username) throws UserauthorizationException {
	List<String> roles = new ArrayList<String>(); 
	try { 			
		roles = UserAuthorization.getUserRole(username,RolesManager.DATABASE_URL,RolesManager.DATABASE_USERNAME,RolesManager.DATABASE_PASSWORD);	
		} 
	catch (UserauthorizationException e) {
 		if(e.getErrorCode()==500) e.printStackTrace(); 
		if(e.getErrorCode()==0) throw new UserauthorizationException(UserauthorizationExceptionCode.USER_NOT_FOUND); 	
		else throw new UserauthorizationException(UserauthorizationExceptionCode.NO_GROUP_SUPPORTING_TABLE); 
		} 		
	return roles;
	}
}

