package com.example.demo.service.impl;

import com.example.demo.dao.IUserRoleDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService,  UserService {
	

	//private UserDao userDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IUserRoleDao userRoleDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid email or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
	}

	private Set<GrantedAuthority> getAuthority(User user) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		userRoleDao.getRoles(user.getId()).forEach(role -> {
			//authorities.add(new SimpleGrantedAuthority(role.getName()));
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}




	/*
	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userDao.deleteById(id);
	}

	@Override
	public User findOne(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id).get();
	}*/

	@Override
    public User save(UserDto user) {
	    User newUser = new User();
	    newUser.setEmail(user.getEmail());
	    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setFname(user.getFname());
		newUser.setSname(user.getSname());
        return userDao.save(newUser);
    }


}
