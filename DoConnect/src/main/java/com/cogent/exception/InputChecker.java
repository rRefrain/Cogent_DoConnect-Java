package com.cogent.exception;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;


/**
 * Utility Class to check inputs
 * @author michaelmiranda
 * @since 1.0
 */
public class InputChecker {
	
	/**
	 * Helper function to see if optional is null or empty
	 * @param optional to check if null or empty
	 * 
	 * @throws NoSuchElementException if optional is null or empty
	 * @see GlobalControllerExceptionHandler#handleNoSuchElement()
	 * @since 1.0
	 */
	public static <T> void checkOptionalIsEmpty(Optional<T> optional) throws NoSuchElementException  {
		if (optional == null || optional.isEmpty()) {
			throw new NoSuchElementException("Not Found");
		}
	}
	
	/**
	 * Helper function to see if object is null
	 * @param o object to check if null
	 * 
	 * @throws BadFormExcpetion if object is null
	 * @see GlobalControllerExceptionHandler#handleBadJSON()
	 * @since 1.0
	 */
	public static void checkObjectIsNull(Object o) throws DataIntegrityViolationException {
		if (o == null) {
			throw new DataIntegrityViolationException("Is null");
		}
	}
	
}
