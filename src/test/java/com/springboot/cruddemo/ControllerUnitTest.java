package com.springboot.cruddemo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.cruddemo.dao.EmployeeRepository;
import com.springboot.cruddemo.entity.Employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EmployeeRepository employeeRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void GetAllEmployeesReturningTest() throws Exception {
		String result = this.JSONtoString(getDummyEmployeeList());
		when(employeeRepository.findAll()).thenReturn(getDummyEmployeeList());
		mockMvc.perform(get("/api/employees"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(content().json(result));
	}

	@Test
	public void GetAllEmployeesFailingTest() throws Exception {
		when(employeeRepository.findAll()).thenReturn(getEmptyEmployeeList());
		mockMvc.perform(get("/api/employees"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.statusCode").value(404))
				.andExpect(jsonPath("$.message").value("No Records Exist"))
				.andExpect(jsonPath("$.timeStamp").exists());
	}

	private List<Employee> getDummyEmployeeList() {
		List<Employee> employees = new ArrayList<>();
		employees.add(buildEmployee());
		employees.add(buildEmployee());
		return employees;
	}

	private List<Employee> getEmptyEmployeeList() {
		List<Employee> employees = new ArrayList<>();
		return employees;
	}

	public Employee buildEmployee() {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstName("Akashdeep");
		employee.setLastName("Bhattacharya");
		employee.setEmail("beingakscool@gmail.com");
		return employee;
	}

	private String JSONtoString(Object o) throws Exception {
		return new ObjectMapper().writeValueAsString(o);
	}
}
