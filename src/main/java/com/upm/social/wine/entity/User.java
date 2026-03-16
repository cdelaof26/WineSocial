package com.upm.social.wine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

/**
 * User model
 * @author cristopher
 */
@Entity
@Table(name = "user", schema = "wdb", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends RepresentationModel<User> {
    /**
     * Auto incremental id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental del usuario", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    /**
     * The username, it must have between 1 and 128 characters (inclusive)
     */
    @NotEmpty(message = "El nombre es obligatorio")
    @Schema(description = "Identificador autoincremental del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    
    /**
     * Birth date, date format is YYYY-MM-DD
     */
    @NotEmpty(message = "La fecha de nacimiento es obligatoria")
    @Schema(description = "Fecha de nacimiento del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
    private String birthdate;
    
    /**
     * Email, it must have between 1 and 128 characters (inclusive)
     */
    @Email
    @Schema(description = "Correo del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    /**
     * Empty constructor
     */
    public User() { }

    /**
     * User constructor
     * 
     * @param id unique integer id
     * @param username the username
     * @param birthdate the user's birth date
     * @param email the user's email
     */
    public User(Integer id, String username, String birthdate, String email) {
        this.id = id;
        this.username = username;
        this.birthdate = birthdate;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User usr)
            return username != null && username.equals(usr.username);
        return false;
    }

    @Override
    public int hashCode() {
        if (username == null)
            return Integer.MIN_VALUE;
        
        return username.hashCode();
    }
}
