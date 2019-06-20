package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Employee;
import com.filipiakp.warehousespring.model.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {


	private EmployeeRepository employeeRepository;

	@GetMapping
	public List<Employee> getEmployees(){

		return employeeRepository.findAll();
	}

}
