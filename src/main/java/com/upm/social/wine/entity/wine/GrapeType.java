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
 * Grape type model
 * @author cristopher
 */
@Entity
@Table(name = "grape_type", schema = "wdb", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrapeType extends RepresentationModel<GrapeType> {
    /**
     * Auto incremental id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental del tipo de uva", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    /**
     * Grape type name, it must have between 1 and 128 characters (inclusive)
     */
    @NotEmpty(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del tipo de uva", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    public GrapeType() { }

    public GrapeType(Integer id, String name) {
        this.id = id;
        this.name = name;
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
    
    @Override
    public String toString() {
        if (id != null)
            return String.format("grapetype:%d:%s", id, name);
        return String.format("grapetype:NOID:%s", name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GrapeType usr)
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
