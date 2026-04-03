package com.upm.social.wine.entity.wine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upm.social.wine.exception.FormattedMessageRuntimeException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import java.lang.reflect.Field;
import org.springframework.hateoas.RepresentationModel;

/**
 * Winery model
 * @author cristopher
 */
@Entity
@Table(name = "winery", schema = "wdb", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@JsonIgnoreProperties(ignoreUnknown = true)
public class Winery extends RepresentationModel<Winery> {
    /**
     * Auto incremental id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental de la bodega", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    /**
     * Wine name, it must have between 1 and 128 characters (inclusive)
     */
    @NotEmpty(message = "El nombre es obligatorio")
    @Schema(description = "Nombre de la bodega", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "Dirección postal de la bodega", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String postalAddress;

    public Winery() { }

    public Winery(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Winery(Integer id, String name, String postalAddress) {
        this.id = id;
        this.name = name;
        this.postalAddress = postalAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
    
    @Override
    public String toString() {
        if (id != null)
            return String.format("winery:%d:%s", id, name);
        return String.format("winery:NOID:%s", name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Winery usr)
            return name != null && name.equals(usr.name);
        return false;
    }

    @Override
    public int hashCode() {
        if (name == null)
            return Integer.MIN_VALUE;
        
        return name.hashCode();
    }
    
    /**
     * Copies all the information to this object given another
     * @param o the object to copy from
     */
    public void copyFrom(Object o) {
        if (this.getClass().isInstance(o))
            throw new FormattedMessageRuntimeException("Object 'o' is not instance of '%s'. "
                    + "Therefore it cannot be copied", this.getClass().getName());
            
        for (Field f : this.getClass().getDeclaredFields())
            try {
                f.set(this, f.get(o));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                System.getLogger(this.getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
    }
}
