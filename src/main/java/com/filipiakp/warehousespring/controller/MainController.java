package com.filipiakp.warehousespring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class MainController {

	@GetMapping
	public String getHomepage(){
		return "index";
	}
}
