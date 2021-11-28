package com.springboot.cruddemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.cruddemo.entity.AuthUser;
import com.springboot.cruddemo.response.AuthenticationResponse;
import com.springboot.cruddemo.service.AuthService;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthUser authUser) throws Exception
	{
		AuthenticationResponse response = new AuthenticationResponse("User Logged In",authService.login(authUser));
		return ResponseEntity.ok(response);
	}
	
	 @PostMapping("/signup")
	    public ResponseEntity<?> createUser(@RequestBody AuthUser aucUser) throws Exception{
	        System.out.println(aucUser.toString());
	        AuthenticationResponse response = new AuthenticationResponse("User Signup Successful",authService.createUser(aucUser));
			return ResponseEntity.ok(response);
	    }
}
