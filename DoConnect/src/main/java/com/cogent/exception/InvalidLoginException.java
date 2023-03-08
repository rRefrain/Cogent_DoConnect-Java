package com.cogent.exception;

/***
 * Custom Exception Class to notify Server of Invalid Login Attempts
 * 
 * @see java.util.Exception
 * 
 * @author michaelmiranda
 * @since 1.0
 */
public class InvalidLoginException extends Exception {
	public InvalidLoginException(String errorMessage) {
		super(errorMessage);
	}
	private static final long serialVersionUID = 1L;

}
