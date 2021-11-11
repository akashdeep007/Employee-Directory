package com.springboot.cruddemo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.cruddemo.dao.EmployeeRepository;
import com.springboot.cruddemo.entity.Employee;

@Service
public class EmployeeServiceJPARepositoryImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	@Transactional
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	@Transactional
	public Employee findEmployee(int employeeId) {
		return employeeRepository.getById(employeeId);
	}

	@Override
	@Transactional
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);

	}

	@Override
	@Transactional
	public void deleteEmployee(int employeeId) {
		Employee employee = employeeRepository.getById(employeeId);
		employeeRepository.delete(employee);

	}

}
