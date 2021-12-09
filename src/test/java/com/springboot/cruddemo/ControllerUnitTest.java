package com.springboot.cruddemo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.cruddemo.dao.EmployeeRepository;
import com.springboot.cruddemo.entity.Employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
	public void GetAllEmployeesWorkingTest() throws Exception {
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
	public void GetAllEmployeesNotFoundTest() throws Exception {
		when(employeeRepository.findAll()).thenReturn(getEmptyEmployeeList());
		mockMvc.perform(get("/api/employees"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.statusCode").value(404))
				.andExpect(jsonPath("$.message").value("No Records Exist"))
				.andExpect(jsonPath("$.timeStamp").exists());
	}

	@Test
	public void GetAEmployeeByIdWorkingTest() throws Exception {
		String result = this.JSONtoString(buildEmployee());
		Optional<Employee> employee = Optional.of(buildEmployee());
		when(employeeRepository.findById(any())).thenReturn(employee);
		mockMvc.perform(get("/api/employees/" + employee.get().getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(result));
	}

	@Test
	public void GetAEmployeeByIdNotFoundTest() throws Exception {
		Optional<Employee> employee = Optional.empty();
		when(employeeRepository.findById(any())).thenReturn(employee);
		mockMvc.perform(get("/api/employees/22222"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.statusCode").value(404))
				.andExpect(jsonPath("$.message").value("Employee id - 22222 not found"))
				.andExpect(jsonPath("$.timeStamp").exists());
	}

	@Test
	public void GetAEmployeeByIdWrongPathVariableTest() throws Exception {
		Optional<Employee> employee = Optional.empty();
		when(employeeRepository.findById(any())).thenReturn(employee);
		mockMvc.perform(get("/api/employees/xyz"))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.statusCode").value(400))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.timeStamp").exists());
	}

	@Test
	public void AddAEmployeeWorkingTest() throws Exception {
		when(employeeRepository.save(any())).thenReturn(buildPostEmployee());
		String jsonString = this.JSONtoString(buildPostEmployee());
		String result = this.JSONtoString(buildPostEmployee());
		mockMvc.perform(post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(result));
	}

	@Test
	public void AddAEmployeeWrongDataTest() throws Exception {
		when(employeeRepository.save(any())).thenReturn(buildEmployeeWithNumbers());
		String jsonString = this.JSONtoString(buildEmployeeWithNumbers());
		mockMvc.perform(post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andDo(print())
				.andExpect(status().isNotAcceptable())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.statusCode").value(406))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.timeStamp").exists());
	}

	@Test
	public void AddAEmployeeNoDataTest() throws Exception {
		when(employeeRepository.save(any())).thenReturn(buildEmployeeWithNumbers());
		String jsonString = "";
		mockMvc.perform(post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.statusCode").value(400))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.timeStamp").exists());
	}

	@Test
	public void UpdateAEmployeeWorkingTest() throws Exception {
		Optional<Employee> employee = Optional.of(buildEmployee());
		when(employeeRepository.findById(any())).thenReturn(employee);
		when(employeeRepository.save(any())).thenReturn(buildEmployee());
		String jsonString = this.JSONtoString(buildEmployee());
		String result = this.JSONtoString(buildEmployee());
		mockMvc.perform(put("/api/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(result));
	}

	@Test
	public void UpdateAEmployeeWithWrongIdTest() throws Exception {
		Optional<Employee> employee = Optional.empty();
		when(employeeRepository.findById(any())).thenReturn(employee);
		when(employeeRepository.save(any())).thenReturn(buildEmployee());
		String jsonString = this.JSONtoString(buildEmployee());
		mockMvc.perform(put("/api/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.statusCode").value(404))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.timeStamp").exists());
	}

	@Test
	public void UpdateAEmployeeWithNoDataTest() throws Exception {
		Optional<Employee> employee = Optional.empty();
		when(employeeRepository.findById(any())).thenReturn(employee);
		when(employeeRepository.save(any())).thenReturn(buildEmployee());
		mockMvc.perform(put("/api/employees")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.statusCode").value(400))
				.andExpect(jsonPath("$.message").exists())
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

	public Employee buildPostEmployee() {
		Employee employee = new Employee();
		employee.setId(0);
		employee.setFirstName("Akashdeep");
		employee.setLastName("Bhattacharya");
		employee.setEmail("beingakscool@gmail.com");
		return employee;
	}

	public Employee buildEmployeeWithNumbers() {
		Employee employee = new Employee();
		employee.setFirstName("123");
		employee.setLastName("123");
		employee.setEmail("beingakscool@gmail.com");
		return employee;
	}

	private String JSONtoString(Object o) throws Exception {
		return new ObjectMapper().writeValueAsString(o);
	}
}
