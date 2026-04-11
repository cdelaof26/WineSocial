package com.upm.social.wine.entity.wine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

/**
 * Wine type model
 * @author cristopher
 */
@Entity
@Table(name = "wine_type", schema = "wdb", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@JsonIgnoreProperties(ignoreUnknown = true)
public class WineType extends RepresentationModel<WineType> {
    /**
     * Auto incremental id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental del tipo de vino", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    /**
     * WineType name, it must have between 1 and 128 characters (inclusive)
     */
    @NotEmpty(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del tipo de vino", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "Descripción del tipo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    public WineType() { }

    public WineType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public WineType(Integer id, String name, String postalAddress) {
        this.id = id;
        this.name = name;
        this.description = postalAddress;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        if (id != null)
            return String.format("winetype:%d:%s", id, name);
        return String.format("winetype:NOID:%s", name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WineType usr)
            return name != null && name.equals(usr.name);
        return false;
    }

    @Override
    public int hashCode() {
        if (name == null)
            return Integer.MIN_VALUE;
        
        return name.hashCode();
    }
}
