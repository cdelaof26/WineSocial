package com.upm.social.wine.entity.wine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.sql.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

/**
 * Wine model
 * @author cristopher
 */
@Entity
@Table(name = "wine", schema = "wdb")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wine extends RepresentationModel<Wine> {
    /**
     * Auto incremental id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental del vino", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    /**
     * Wine name, it must have between 1 and 128 characters (inclusive)
     */
    @NotEmpty(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del vino", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "Bodega", requiredMode = Schema.RequiredMode.REQUIRED)
    @OneToOne
    @JoinColumn(name = "winery_id", referencedColumnName = "id")
    private Winery winery;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Añada de la botella", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date vintage;
    
    @Schema(description = "Denominación de origen de la botella", requiredMode = Schema.RequiredMode.REQUIRED)
    @OneToOne
    @JoinColumn(name = "pdo_id", referencedColumnName = "id")
    private PDO pdo;
    
    @Schema(description = "Tipo de vino", requiredMode = Schema.RequiredMode.REQUIRED)
    @OneToOne
    @JoinColumn(name = "wine_type_id", referencedColumnName = "id")
    private WineType winetype;

    public Wine() { }

    public Wine(Integer id, String name, Winery winery, Date vintage, PDO pdo, WineType winetype) {
        this.id = id;
        this.name = name;
        this.winery = winery;
        this.vintage = vintage;
        this.pdo = pdo;
        this.winetype = winetype;
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

    public Winery getWinery() {
        return winery;
    }

    public void setWinery(Winery winery) {
        this.winery = winery;
    }

    public Date getVintage() {
        return vintage;
    }

    public void setVintage(Date vintage) {
        this.vintage = vintage;
    }

    public PDO getPdo() {
        return pdo;
    }

    public void setPdo(PDO pdo) {
        this.pdo = pdo;
    }

    public WineType getWinetype() {
        return winetype;
    }

    public void setWinetype(WineType winetype) {
        this.winetype = winetype;
    }

    @Override
    public String toString() {
        if (id != null)
            return String.format("wine:%d:%s", id, name);
        return String.format("wine:NOID:%s", name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Wine usr)
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
