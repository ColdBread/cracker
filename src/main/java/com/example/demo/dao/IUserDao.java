package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;


public interface IUserDao {
    public User save (User user);
    public User findByUsername(String username);
}
