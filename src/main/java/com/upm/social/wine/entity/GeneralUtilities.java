package com.upm.social.wine.entity;

import com.upm.social.wine.exception.FormattedMessageRuntimeException;
import java.lang.reflect.Field;

/**
 * General utilities
 * @author cristopher
 */
public class GeneralUtilities {
    /**
     * Copies all the information of 'a' to 'b'
     * @param a the source object
     * @param b the destination object
     * @throws RuntimeException if objects are not the same class
     */
    public static void copyAToB(Object a, Object b) {
        if (a.getClass().isInstance(b))
            throw new FormattedMessageRuntimeException("Object 'a' is instance of '%s' and 'b' is instance of '%s'. "
                    + "Therefore 'a' cannot be to 'b'", a.getClass().getName(), b.getClass().getName());
        
        for (Field f : a.getClass().getDeclaredFields())
            try {
                f.set(a, f.get(b));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                System.getLogger(a.getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
    }
}
