package com.upm.social.wine.exception.wine;

import com.upm.social.wine.exception.FormattedMessageRuntimeException;

/**
 * RuntimeException thrown when a winery name is already taken
 * @author cristopher
 */
public class WineryExistsException extends FormattedMessageRuntimeException {
    public WineryExistsException(String name) {
        super("El nombre '%s' ya esta en uso", name);
    }
}
