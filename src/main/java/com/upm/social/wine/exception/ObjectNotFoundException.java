package com.upm.social.wine.exception;

/**
 * RuntimeException thrown when an object is not found by its ID
 * @author cristopher
 */
public class ObjectNotFoundException extends FormattedMessageRuntimeException {
    /**
     * Creates a new FormattedMessageRuntimeException with a message that reads
     * "{@code entityName} '{@code id}' no existe"
     * @param entityName the name of the entity
     * @param id the id
     */
    public ObjectNotFoundException(String entityName, Number id) {
        super("%s '%d' no existe", entityName, id);
    }
}
