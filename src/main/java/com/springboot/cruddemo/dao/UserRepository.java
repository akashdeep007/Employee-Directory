package com.springboot.cruddemo.dao;

import com.springboot.cruddemo.entity.AuthUser;

public interface UserRepository {

	public AuthUser loadUserByUsername(String username);

	public void save(AuthUser authUser);
}
