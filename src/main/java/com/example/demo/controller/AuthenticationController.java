package com.example.demo.controller;

import com.example.demo.config.TokenProvider;
import com.example.demo.model.AuthToken;
import com.example.demo.model.LoginUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        System.out.println("-----------------------------------------");
        System.out.println(loginUser.getEmail());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getEmail());

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        bcryptEncoder.encode(loginUser.getPassword()),
                        userDetails.getAuthorities()
                )
        );

        System.out.println("-----------------------------------------");
        System.out.println(loginUser.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        //String token = "abc";
        System.out.println(new AuthToken(token));
        return ResponseEntity.ok(new AuthToken(token));
    }

}
