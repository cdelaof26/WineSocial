package com.upm.social.wine.exception;

/**
 * RuntimeException thrown when a value is already taken
 * @author cristopher
 */
public class ObjectExistsException extends FormattedMessageRuntimeException {
    /**
     * Creates a new FormattedMessageRuntimeException with a message that reads
     * "El nombre '{@code value}' ya está en uso"
     * @param value the value
     */
    public ObjectExistsException(String value) {
        super("El nombre '%s' ya está en uso", value);
    }
    
    /**
     * Creates a new FormattedMessageRuntimeException with a message that reads
     * "{@code fieldName} '{@code name}' ya está en uso"
     * @param fieldName the name of the field
     * @param value the value
     */
    public ObjectExistsException(String fieldName, String value) {
        super("%s '%s' ya está en uso", fieldName, value);
    }
}
