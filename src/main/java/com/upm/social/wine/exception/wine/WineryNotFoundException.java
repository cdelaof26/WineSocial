package com.upm.social.wine.exception.wine;

import com.upm.social.wine.exception.FormattedMessageRuntimeException;

/**
 * RuntimeException thrown when a winery is not found via its ID
 * @author cristopher
 */
public class WineryNotFoundException extends FormattedMessageRuntimeException {
    public WineryNotFoundException(Number id) {
        super("La bodega '%s' no existe", id);
    }
}
