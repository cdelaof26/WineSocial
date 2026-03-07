package com.upm.social.wine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WineSocial is an API REST prototype built with SpringBoot
 * 
 * @author cristopher
 */
@SpringBootApplication
public class WineSocial {
    /**
     * Main method
     * @param args no arguments are taken other than the ones for SpringBoot's
     * functionality
     */
    public static void main(String[] args) {
        SpringApplication.run(WineSocial.class, args);
    }
}
