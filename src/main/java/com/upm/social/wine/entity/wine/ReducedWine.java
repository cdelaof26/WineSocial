package com.upm.social.wine.entity.wine;

import java.sql.Date;
import org.springframework.hateoas.RepresentationModel;

/**
 * Reduced wine model
 * @author cristopher
 */
public class ReducedWine extends RepresentationModel<ReducedWine> {
    private Integer id;
    private String name;
    private String wineryName;
    private Date vintage;
    private String pdoName;
    private String winetypeName;

    public ReducedWine() { }

    public ReducedWine(Integer id, String name, String wineryName, Date vintage, String pdoName, String winetypeName) {
        this.id = id;
        this.name = name;
        this.wineryName = wineryName;
        this.vintage = vintage;
        this.pdoName = pdoName;
        this.winetypeName = winetypeName;
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

    public String getWineryName() {
        return wineryName;
    }

    public void setWineryName(String wineryName) {
        this.wineryName = wineryName;
    }

    public Date getVintage() {
        return vintage;
    }

    public void setVintage(Date vintage) {
        this.vintage = vintage;
    }

    public String getPdoName() {
        return pdoName;
    }

    public void setPdoName(String pdoName) {
        this.pdoName = pdoName;
    }

    public String getWinetypeName() {
        return winetypeName;
    }

    public void setWinetypeName(String winetypeName) {
        this.winetypeName = winetypeName;
    }

    @Override
    public String toString() {
        if (id != null)
            return String.format("rwine:%d:%s", id, name);
        return String.format("rwine:NOID:%s", name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReducedWine usr)
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
