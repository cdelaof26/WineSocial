package com.upm.social.wine.exception;

/**
 * RuntimeException with formatted message
 * @author cristopher
 */
public class FormattedMessageRuntimeException extends RuntimeException {
    public FormattedMessageRuntimeException(String formattedMessage, Object ... args) {
        super(String.format(formattedMessage, args));
    }
}
