package com.tracker.cowin.batch.exceptions;

public abstract class BaseException extends Exception{
	private static final long serialVersionUID = -1372987627452557723L;
	protected BaseException(String errorMessage) {
		super(errorMessage);
	}

}
