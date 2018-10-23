package com.example.demo.dao.impl;

import com.example.demo.dao.IUserDao;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.UUID;

@Repository(value = "userDao")
public class UserDao implements IUserDao {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
/*
    @Bean
    public IUserDao userDao() {
        return new UserDao();
    }
*/

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public User save(User user){
        String sql = "INSERT INTO users"+
                " (id ,email, password, s_name, f_name) VALUES (?, ?, ?, ?, ?);"+
                "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate = new JdbcTemplate(dataSource);
        String id = UUID.randomUUID().toString();
        int role_id = 2; //Role_User
        jdbcTemplate.update(sql, new Object[] {id ,user.getEmail(), user.getPassword(),
        user.getSname(), user.getFname(), id, role_id});
        User result = new User();
        result.setId(id);
        result.setFname(user.getFname());
        //result.setPassword(bcryptEncoder. user.getPassword());
        result.setSname(user.getSname());
        result.setEmail(user.getEmail());
        return result;
    }

    @Override
    public User findByUsername(String email) {
        String sql = "select * from users where email = ?";
        jdbcTemplate = new JdbcTemplate(dataSource);
        User user = (User) jdbcTemplate.queryForObject(sql, new Object[]{email},
                (rs, rowNum) -> {
                    User user1 = new User();
                    user1.setId(rs.getString("id"));
                    user1.setEmail(rs.getString("email"));
                    user1.setSname(rs.getString("s_name"));
                    user1.setFname(rs.getString("f_name"));
                    user1.setPassword(rs.getString("password"));
                    return user1;
                });
        System.out.println("--------------kek---");
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        return user;
    }
}
