package com.upm.social.wine.exception.wine;

import com.upm.social.wine.exception.user.*;
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
public class WineryExceptionAdvice {
    @ExceptionHandler(WineryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage wineryNotFoundHandler(WineryNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(WineryExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage wineryFoundHandler(WineryExistsException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(WineryPostalTooLongException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage tooLongPostalHandler(WineryPostalTooLongException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
