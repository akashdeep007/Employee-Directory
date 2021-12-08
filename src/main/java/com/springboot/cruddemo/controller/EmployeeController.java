package com.springboot.cruddemo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.cruddemo.dao.EmployeeRepository;
import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.exception.EmployeeNotFoundException;
//import com.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
//	private EmployeeService employeeService;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() throws EmployeeNotFoundException {
//		List<Employee> employees = employeeService.findAll();
		List<Employee> employees = employeeRepository.findAll();
		if (employees.isEmpty())
			throw new EmployeeNotFoundException("No Records Exist");
		return employees;
	}

	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) throws EmployeeNotFoundException {
//		Employee employee = employeeService.findEmployee(employeeId);
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isEmpty())
			throw new EmployeeNotFoundException("Employee id - " + employeeId + " not found ");
		return employee.get();
	}

	@PostMapping("/employees")
	public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
		employee.setId(0);
//		employeeService.saveEmployee(employee);
		employeeRepository.save(employee);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getId())
				.toUri();
		return ResponseEntity.created(location).body(employee);

	}

	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee employee) {
//		employeeService.saveEmployee(employee);
		employeeRepository.save(employee);
		return employee;

	}

	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
//		Employee employee = employeeService.findEmployee(employeeId);
		Employee employee = employeeRepository.getById(employeeId);
//		if (employee == null)
//			throw new EmployeeNotFoundException("Employee id - " + employeeId + " not found ");
//		employeeService.deleteEmployee(employeeId);
		employeeRepository.delete(employee);
		return "Employee Deleted";

	}

}
