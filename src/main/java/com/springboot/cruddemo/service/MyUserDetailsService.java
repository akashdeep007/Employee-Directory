package com.springboot.cruddemo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.cruddemo.dao.UserRepository;
import com.springboot.cruddemo.entity.AuthUser;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		   AuthUser user = userRepository.loadUserByUsername(username);
	        if(user == null){
	            throw new UsernameNotFoundException("User does not exist.");
	        }
	        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

}
