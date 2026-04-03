package com.upm.social.wine.exception.user;

import com.upm.social.wine.exception.FormattedMessageRuntimeException;

/**
 * RuntimeException thrown when a user is not found via its ID
 * @author cristopher
 */
public class UserNotFoundException extends FormattedMessageRuntimeException {
    public UserNotFoundException(Number id) {
        super("El usuario '%s' no existe", id);
    }
}
