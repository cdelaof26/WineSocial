package com.upm.social.wine.exception;

/**
 * RuntimeException with formatted message
 * @author cristopher
 */
public class FormattedMessageRuntimeException extends RuntimeException {
    /**
     * RuntimeException with formatted message using {@code String.format}
     * @param formattedMessage a format string
     * @param args the arguments referenced by the format
     */
    public FormattedMessageRuntimeException(String formattedMessage, Object ... args) {
        super(String.format(formattedMessage, args));
    }
}
