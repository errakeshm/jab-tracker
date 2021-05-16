package com.tracker.cowin.batch.exceptions;

import org.springframework.http.HttpStatus;

import com.tracker.cowin.batch.constants.CommonConstants;

public class InvalidResponseException extends BaseException {
	/**
	 * 
	 */
	private HttpStatus httpStatus;
	private String errorCode;
	
	private static final long serialVersionUID = -4311437389712473403L;
	private static final String BASE_ERROR_CODE = ExceptionCodes.INVALID_HTTP_RESPONSE;
	
	private static final String ERROR_COWIN_RESPONSE = BASE_ERROR_CODE + CommonConstants.HYPHEN + "10001";
	
	private InvalidResponseException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}
	
	public InvalidResponseException(HttpStatus httpStatus, String errorMessage) {
		super(errorMessage);
		this.httpStatus = httpStatus;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
