package com.springboot.cruddemo.service;

import java.util.List;

import com.springboot.cruddemo.entity.Employee;

public interface EmployeeService {

	public List<Employee> findAll();

	public Employee findEmployee(int employeeId);

	public void saveEmployee(Employee employee);

	public void deleteEmployee(int employeeId);


}
