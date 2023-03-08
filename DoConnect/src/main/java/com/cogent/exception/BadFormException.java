package com.cogent.exception;

/***
 * Custom Exception Class to notify Server of bad data form
 * 
 * @see java.util.Exception
 * 
 * @author michaelmiranda
 * @since 1.0
 */
public class BadFormException extends Exception {
	public BadFormException(String errorMessage) {
		super(errorMessage);
	}
	private static final long serialVersionUID = 1L;
}