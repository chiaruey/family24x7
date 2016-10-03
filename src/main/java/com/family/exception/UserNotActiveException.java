package com.family.exception;

public class UserNotActiveException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserNotActiveException() {
		super();
	}
	
	public UserNotActiveException(String message) {
		super(message);
	}
	
	public UserNotActiveException(Throwable cause) {
		super(cause);
	}
	
	public UserNotActiveException(String message, Throwable cause) {
		super(message, cause);
	}

}
