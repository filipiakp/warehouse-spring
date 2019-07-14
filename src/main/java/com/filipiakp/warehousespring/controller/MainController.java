package com.filipiakp.warehousespring.controller;

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

//	@RequestMapping("/error")
//	public String getErrorPage(Model model){
//		return "";
//	}

}
