package com.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<Employee> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Employee> query = currentSession.createQuery("from Employee",Employee.class);
		List<Employee> employees= query.getResultList();
		return employees;
	}

}
