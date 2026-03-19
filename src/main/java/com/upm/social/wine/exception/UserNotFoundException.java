package com.upm.social.wine.exception;

/**
 * RuntimeException thrown when a user is not found via its ID
 * @author cristopher
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Number id) {
        super(String.format("El usuario '%s' no existe", id));
    }
}
