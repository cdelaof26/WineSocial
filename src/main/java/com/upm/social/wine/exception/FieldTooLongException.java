package com.upm.social.wine.exception;

/**
 * RuntimeException thrown when a name has more than 128 characters
 * @author cristopher
 */
public class FieldTooLongException extends FormattedMessageRuntimeException {
    /**
     * Creates a new FormattedMessageRuntimeException with a message that reads
     * "El nombre excede la longitud de caracteres máxima permitida [{@code length} > 128]"
     * @param length the illegal length
     */
    public FieldTooLongException(Number length) {
        super("El nombre excede la longitud de caracteres máxima permitida [%d > 128]", length);
    }
    
    /**
     * Creates a new FormattedMessageRuntimeException with a message that reads
     * "{@code fieldName} excede la longitud de caracteres máxima permitida [{@code length} > 128]"
     * @param fieldName the name of the field with illegal length
     * @param length the illegal length
     */
    public FieldTooLongException(String fieldName, Number length) {
        super("%s excede la longitud de caracteres máxima permitida [%d > 128]", fieldName, length);
    }
    
    /**
     * Creates a new FormattedMessageRuntimeException with a message that reads
     * "{@code fieldName} excede la longitud de caracteres máxima permitida [{@code length} > {@code maxLength}]"
     * @param fieldName the name of the field with illegal length
     * @param length the illegal length
     * @param maxLength the maximum allowed length
     */
    public FieldTooLongException(String fieldName, Number length, Number maxLength) {
        super("%s excede la longitud de caracteres máxima permitida [%d > %d]", fieldName, length, maxLength);
    }
}
