package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@RequestMapping("/")
	public String getHomepage(){
		return "index";
	}

	//TODO: repository, sproboj tego thymeleaf https://www.baeldung.com/thymeleaf-list
	@RequestMapping("/employees")
	public String getEmployeePage(Model model){
		model.addAttribute("employees",employeeRepository.findAll());
		return "employees";
	}
}
