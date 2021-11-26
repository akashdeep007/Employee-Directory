package com.springboot.cruddemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.springboot.cruddemo.entity.AuthUser;
import com.springboot.cruddemo.util.JWTUtil;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	public String login(AuthUser authUser) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getPassword()));
			
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Username or Password Incorrect");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authUser.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		return jwt;		
	}

}
