package com.family.exception;

public class AlreadyRegisteredException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AlreadyRegisteredException() {
		super();
	}
	
	public AlreadyRegisteredException(String message) {
		super(message);
	}
	
	public AlreadyRegisteredException(Throwable cause) {
		super(cause);
	}
	
	public AlreadyRegisteredException(String message, Throwable cause) {
		super(message, cause);
	}

}
