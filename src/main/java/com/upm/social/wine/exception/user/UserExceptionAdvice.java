package com.upm.social.wine.exception.user;

import com.upm.social.wine.exception.FieldTooLongException;
import com.upm.social.wine.exception.ObjectExistsException;
import com.upm.social.wine.exception.ObjectNotFoundException;
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
    @ExceptionHandler(InvalidUserAge.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage underagedHandler(InvalidUserAge ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
