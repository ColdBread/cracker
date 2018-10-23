package com.example.demo.dao;

import com.example.demo.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface IUserRoleDao {
    public List<Role> getRoles(String user_id);
}
