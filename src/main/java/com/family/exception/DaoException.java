package com.family.exception;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 4544127961745745030L;

	public DaoException() {}
	
	public DaoException(Throwable cause) {
		super(cause);
	}
	
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
