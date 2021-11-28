package com.springboot.cruddemo.response;

public class AuthenticationResponse {

	private String message;
	private String jwt;

	public AuthenticationResponse(String message,String jwt) {
		super();
		this.jwt = jwt;
		this.message=message;
	}
	public AuthenticationResponse() {

	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
