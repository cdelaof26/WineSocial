package com.upm.social.wine.exception.user;

import com.upm.social.wine.exception.FormattedMessageRuntimeException;

/**
 * RuntimeException thrown when a user is underaged
 * @author cristopher
 */
public class InvalidUserAge extends FormattedMessageRuntimeException {
    public InvalidUserAge(Number age) {
        super("Solo se pueden registrar usuarios con 18 o más años cumplidos (%d)", age);
    }
}

