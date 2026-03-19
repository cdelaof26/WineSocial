package com.upm.social.wine.exception;

/**
 * RuntimeException thrown when an email is already taken
 * @author cristopher
 */
public class UserExistsException extends RuntimeException {
    public UserExistsException(String email) {
        super(String.format("El correo '%s' ya esta en uso", email));
    }
}
