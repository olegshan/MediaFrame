package com.olegshan.mediabox.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Bors on 14.05.2016.
 */
@Entity
public class UserClient {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name must be at least 2 characters!")
    @Column(unique = true)
    private String name;

    @Size(min = 1, message = "Invalid email address!")
    @Email(message = "Invalid email address!")
    private String email;

    @Size(min = 5, message = "Password must be at least 5 characters!")
    private String password;

    @ManyToMany
    @JoinTable
    private List<Role> roles;

    @OneToMany(mappedBy = "userClient")
    private List<Photo> photos;

    private boolean enabled;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean ennabled) {
        this.enabled = ennabled;
    }
}
