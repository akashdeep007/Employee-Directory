package com.springboot.cruddemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.exception.EmployeeNotFoundException;
import com.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() throws EmployeeNotFoundException {
		List<Employee> employees = employeeService.findAll();
		if (employees.isEmpty())
			throw new EmployeeNotFoundException("No Records Exist");
		return employees;
	}

	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) throws EmployeeNotFoundException {
		Employee employee = employeeService.findEmployee(employeeId);
		if (employee == null)
			throw new EmployeeNotFoundException("Employee id - " + employeeId + " not found ");
		return employee;
	}

	@PostMapping("/employees")
	public Employee addEmployee(@Valid @RequestBody Employee employee) {
		employee.setId(0);
		employeeService.saveEmployee(employee);
		return employee;

	}

	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee employee) {
		employeeService.saveEmployee(employee);
		return employee;

	}

	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) throws EmployeeNotFoundException {
		Employee employee = employeeService.findEmployee(employeeId);
		if (employee == null)
			throw new EmployeeNotFoundException("Employee id - " + employeeId + " not found ");
		employeeService.deleteEmployee(employeeId);
		return "Employee Deleted";

	}

}
