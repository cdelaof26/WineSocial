package com.upm.social.wine.entity;

import org.springframework.hateoas.RepresentationModel;

/**
 * Reduced user model that only contains ID and username
 * @author cristopher
 */
public class ReducedUser extends RepresentationModel<ReducedUser> {
    private Integer id;
    private String username;

    public ReducedUser() { }

    public ReducedUser(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    
    @Override
    public String toString() {
        if (id != null)
            return String.format("ruser:%d:%s", id, username);
        return String.format("ruser:NOID:%s", username);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReducedUser usr)
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
