package com.springboot.cruddemo.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.cruddemo.entity.AuthUser;;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public AuthUser loadUserByUsername(String username) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<AuthUser> query = currentSession.createQuery("from AuthUser where username=:username", AuthUser.class);
		query.setParameter("username", username);
		AuthUser user = query.uniqueResult();
		return user;
	}

	@Override
	public void save(AuthUser authUser) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(authUser);
		
	}

}
