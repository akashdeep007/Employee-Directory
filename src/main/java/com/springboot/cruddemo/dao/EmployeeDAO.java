package com.springboot.cruddemo.dao;

import java.util.List;

import com.springboot.cruddemo.entity.Employee;

public interface EmployeeDAO {

	public List<Employee> findAll();

	public Employee findEmployee(int employeeId);

	public void saveorUpdateEmployee(Employee employee);

	public void deleteEmployee(int employeeId);


	

}
