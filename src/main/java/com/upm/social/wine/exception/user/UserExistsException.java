package com.upm.social.wine.exception.user;

import com.upm.social.wine.exception.FormattedMessageRuntimeException;

/**
 * RuntimeException thrown when an email is already taken
 * @author cristopher
 */
public class UserExistsException extends FormattedMessageRuntimeException {
    public UserExistsException(String email) {
        super("El correo '%s' ya esta en uso", email);
    }
}
