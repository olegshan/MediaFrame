package com.olegshan.mediabox.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Bors on 22.05.2016.
 */
@Entity
public class Role {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<UserClient> userClients;

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

    public List<UserClient> getUserClients() {
        return userClients;
    }

    public void setUserClients(List<UserClient> userClients) {
        this.userClients = userClients;
    }
}
