package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.model.EmployeeRepository;
import com.filipiakp.warehousespring.model.OrderRepository;
import com.filipiakp.warehousespring.model.ProductRepository;
import com.filipiakp.warehousespring.model.TaskRepository;
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

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private TaskRepository taskRepository;

	@RequestMapping("/")
	public String getHomepage(Model model){
		model.addAttribute("employeesCount", employeeRepository.count());
		model.addAttribute("productsCount", productRepository.count());
		model.addAttribute("ordersCount", orderRepository.count());
		model.addAttribute("ordersFinished", orderRepository.findAll().stream().filter(order -> order.getFinishDate() != null));
		model.addAttribute("ordersInProgress", orderRepository.findAll().stream().filter(order -> order.getFinishDate() == null));
		return "index";
	}

//	@RequestMapping("/error")
//	public String getErrorPage(Model model){
//		return "";
//	}

}
