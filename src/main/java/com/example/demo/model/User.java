package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import javax.persistence.*;
import java.util.Set;


public class User {


    private String id;

    private String email;

    @JsonIgnore
    private String password;

    private String f_name;

    private String s_name;

    /*
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;
*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSname() {
        return s_name;
    }

    public void setSname(String s_name) {
        this.s_name = s_name;
    }

    public String getFname() {
        return f_name;
    }

    public void setFname(String f_name) {
        this.f_name = f_name;
    }
/*
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    */
}
