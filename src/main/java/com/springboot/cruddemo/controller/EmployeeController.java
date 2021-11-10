package com.springboot.cruddemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.cruddemo.dao.EmployeeDAO;
import com.springboot.cruddemo.entity.Employee;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	@Autowired
	private EmployeeDAO employeeDAO;

	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		List<Employee> employees = employeeDAO.findAll();
		return employees;
	}
}
