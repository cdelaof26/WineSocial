package com.upm.social.wine.exception;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handlers
 * @author cristopher
 */
@RestControllerAdvice
public class UserExceptionAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage userNotFoundHandler(UserNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage userFoundHandler(UserExistsException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(InvalidUserAge.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage underagedHandler(InvalidUserAge ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
