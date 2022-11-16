package com.dfx.reverseproxy.security.userauthorizationException;

public enum UserauthorizationExceptionCode {
	DATABASE_FAILURE(500,"Database failure"),
	USER_NOT_FOUND(404,"User not found"),
	NO_GROUP_SUPPORTING_TABLE(400,"Group not found in the supporting table");

	
    private int code;
    private String message;
    
    UserauthorizationExceptionCode(String message) {
        this.message= message;
    }
    
    UserauthorizationExceptionCode(int code,String message) {
        this.code = code;
        this.message= message;
    }

    public int getCode() {
        return code;
    }
    
    public String getErrorMessage() {
    	return message;
    }
}
