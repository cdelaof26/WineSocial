package com.upm.social.wine.exception;

/**
 *
 * @author cristopher
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Number id) {
        super(String.format("El usuario con id %s no existe", id));
    }
}
