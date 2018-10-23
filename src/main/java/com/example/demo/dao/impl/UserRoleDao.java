package com.example.demo.dao.impl;

import com.example.demo.dao.IUserRoleDao;
import com.example.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository(value = "userRoleDao")
public class UserRoleDao implements IUserRoleDao {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
/*
    @Bean
    public IUserRoleDao userRoleDao() {
        return new UserRoleDao();
    }
*/

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public List<Role> getRoles(String user_id){
        System.out.println(user_id);
        String sql = "select roles.id, roles.description, roles.name from roles " +
                "join user_roles on ( roles.id = user_roles.role_id )" +
                "where user_roles.user_id = ?";
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Role> roles = new ArrayList<Role>();
        List<Map<String, Object>> rows =  jdbcTemplate.queryForList(sql, new Object [] {user_id});
        System.out.println(rows.get(0));
        for (Map row : rows) {
            Role role = new Role();
            role.setId((Integer) row.get("id"));
            role.setDescription((String)row.get("description"));
            role.setName((String) row.get("name"));
            roles.add(role);
        }
        return roles;
    }

    /*public void save(User user){
        String sql = "INSERT INTO USER_ROLES (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate = new JdbcTemplate(dataSource);
        //String id = UUID.randomUUID().toString();
        int role_id = 2; //Role_User
        jdbcTemplate.update(sql, new Object[] {id ,user.getUsername(), user.getPassword(),
                user.getSalary(), user.getAge(), id, role_id});
        User result = new User();

        return result;
    }*/
}
