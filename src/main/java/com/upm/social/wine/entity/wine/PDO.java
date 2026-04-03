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
 * PDO model
 * @author cristopher
 */
@Entity
@Table(name = "pdo", schema = "wdb", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@JsonIgnoreProperties(ignoreUnknown = true)
public class PDO extends RepresentationModel<PDO> {
    /**
     * Auto incremental id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental de la denominación de origen", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    /**
     * PDO name, it must have between 1 and 128 characters (inclusive)
     */
    @NotEmpty(message = "El nombre es obligatorio")
    @Schema(description = "Nombre de la denominación de origen", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "Dirección postal de la denominación de origen", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String postalAddress;

    public PDO() { }

    public PDO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public PDO(Integer id, String name, String postalAddress) {
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
        if (obj instanceof PDO usr)
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
