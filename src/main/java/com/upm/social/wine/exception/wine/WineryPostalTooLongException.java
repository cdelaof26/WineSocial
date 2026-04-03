package com.upm.social.wine.exception.wine;

import com.upm.social.wine.exception.FormattedMessageRuntimeException;

/**
 * RuntimeException thrown when a winery's postal address is greater than 512 characters
 * @author cristopher
 */
public class WineryPostalTooLongException extends FormattedMessageRuntimeException {
    public WineryPostalTooLongException(Number length) {
        super("La dirección postal es muy larga [%d > 512]", length);
    }
}
