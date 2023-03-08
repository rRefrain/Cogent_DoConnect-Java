package com.cogent.exception;

import java.util.NoSuchElementException;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Global Controller to give specific HTTP Status upon 
 * Server exception
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
    /**
     * Stub to handle NoSuchElementException
     * and return HTTP Code 404 NOT_FOUND to end user
     * 
     * @see java.util.NoSuchElementException;
     * @since 1.0
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(NoSuchElementException.class)
    public void handleNoSuchElement() {}
    
    /**
     * Stub to handle custom InvalidLoginException
     * and return HTTP Code 401 UNAUTHORIZED to end user
     * 
     * @see InvalidLoginException
     * @since 1.0
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
    @ExceptionHandler(InvalidLoginException.class)
    public void handleBadLogin() {}
    
    /**
     * Stub to handle custom Bad Forms of Data
     * and return HTTP Code 400 BAD_REQUEST to end user
     * 
     * @since 1.0
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleBadHTTP() {}
    
    /**
     * Stub to handle custom Bad Forms of Data
     * and return HTTP Code 400 BAD_REQUEST to end user
     * 
     * @since 1.0
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleBadJson() {}
    
    /**
     * Stub to handle NumberFormatException
     * and return HTTP Code 400 BAD_REQUEST to end user
     * 
     * @since 1.0
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(NumberFormatException.class)
    public void handleNumberFormatException() {}
    
    
    /**
     * Stub to handle UsernameNotFoundException
     * and return HTTP Code 400 BAD_REQUEST to end user
     * 
     * @since 1.0
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(UsernameNotFoundException.class)
    public void handleUsernameNotFoundException() {}
    
    
   
    
    /**
     * Stub to handle SpelEvaluationException
     * and return HTTP Code 400 BAD_REQUEST to end user
     * 
     * @since 1.0
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(org.springframework.expression.spel.SpelEvaluationException.class)
    public void handleSpelEvaluationException() {}
   
}