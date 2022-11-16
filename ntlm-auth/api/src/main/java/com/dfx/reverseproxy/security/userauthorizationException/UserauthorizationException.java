package com.dfx.reverseproxy.security.userauthorizationException;

public class UserauthorizationException extends Exception {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorMsg;

	public UserauthorizationException(UserauthorizationExceptionCode code) {
		this.errorMsg = code.getErrorMessage();
		this.errorCode = code.getCode();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}