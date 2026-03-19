package com.upm.social.wine.exception;

/**
 * RuntimeException thrown when a user is underaged
 * @author cristopher
 */
public class InvalidUserAge extends RuntimeException {
    public InvalidUserAge(Number age) {
        super(String.format("Solo se pueden registrar usuarios con 18 o más años cumplidos (%d)", age));
    }
}

