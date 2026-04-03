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
import java.lang.reflect.Field;
import java.sql.Date;
import org.springframework.format.annotation.DateTimeFormat;
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
    @Schema(description = "Nombre del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    
    /**
     * Birth date
     */
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @Schema(description = "Fecha de nacimiento del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date birthdate;
    
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
    public User(Integer id, String username, Date birthdate, String email) {
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        if (id != null)
            return String.format("user:%d:%s:%s:%s", id, username, birthdate.toString(), email);
        return String.format("user:NOID:%s:%s:%s", username, birthdate.toString(), email);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User usr)
            return email != null && email.equals(usr.email);
        return false;
    }

    @Override
    public int hashCode() {
        if (email == null)
            return Integer.MIN_VALUE;
        
        return email.hashCode();
    }
    
    /**
     * Copies all the information to this object given another
     * @param o the user to copy from
     */
    public void copyFrom(User o) {
        for (Field f : this.getClass().getDeclaredFields())
            try {
                f.set(this, f.get(o));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                System.getLogger(this.getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
    }
}
