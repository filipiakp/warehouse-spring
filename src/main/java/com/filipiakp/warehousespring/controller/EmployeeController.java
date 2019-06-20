package com.filipiakp.warehousespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@GetMapping
	public String getEmployees(){
		return "employees";
	}

}
