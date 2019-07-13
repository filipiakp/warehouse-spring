package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Employee;
import com.filipiakp.warehousespring.entities.Product;
import com.filipiakp.warehousespring.model.EmployeeRepository;
import com.filipiakp.warehousespring.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ProductRepository productRepository;

	@RequestMapping("/")
	public String getHomepage(){
		return "index";
	}

//	@MessageMapping("/employees")
//	@SendTo("/listener/employees")
//	public List<Employee> getEmployeesData(String message){
//		return employeeRepository.findAll();
//	}
//
//	@MessageMapping("/products")
//	@SendTo("/listener/products")
//	public List<Product> getProductsData(String message){
//		return productRepository.findAll();
//	}
//
//	@RequestMapping("/addEmployee")
//	public String addEmployee(Model model){
//		model.addAttribute("file","employees");
//		return "employees";
//	}


}
