package com.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOJPAImpl implements EmployeeDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Employee> findAll() {
		
		TypedQuery<Employee> query = entityManager.createQuery("from Employee", Employee.class);
		List<Employee> employee = query.getResultList();
		return employee;
	}

	@Override
	public Employee findEmployee(int employeeId) {

		Employee employee = entityManager.find(Employee.class, employeeId);
		return employee;
	}

	@Override
	public void saveorUpdateEmployee(Employee employee) {
		Employee dbEmployee = entityManager.merge(employee);
		employee.setId(dbEmployee.getId());

	}

	@Override
	public void deleteEmployee(int employeeId) {
		Employee employee = entityManager.find(Employee.class, employeeId);
		entityManager.remove(employee);

	}

}
