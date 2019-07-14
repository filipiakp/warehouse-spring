package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Employee;
import com.filipiakp.warehousespring.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository repository;

	@RequestMapping("/employees/add")
	String add(){
		return "employeeForm";
	}

	@PostMapping(value="/addEmployee", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	ModelAndView addEmployee(@RequestParam Map<String, String> data){
		Employee employee = new Employee();
		employee.setName(data.get("emplName"));
		employee.setSurname(data.get("emplSurname"));
		employee.setApartmentNumber(data.get("emplApartNum"));
		employee.setCity(data.get("emplCity"));
		employee.setHouseNumber(data.get("emplHouseNum"));
		employee.setPosition(data.get("emplPosition"));
		employee.setSalary(Double.parseDouble(data.get("emplSalary")));
		employee.setStreet(data.get("emplStreet"));
		try {
			employee.setEmploymentDate(new SimpleDateFormat("dd.MM.yyyy").parse(data.get("emplDate")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		repository.save(employee);
		return new ModelAndView("redirect:/employees");
	}

	@RequestMapping("/employees")
	String getAll(Model model){
		model.addAttribute("employees",repository.findAll());
		return "employees";
	}

	@RequestMapping("/employees/edit/{id}")
	String edit(@PathVariable int id, Model model){
		model.addAttribute("employee",repository.findById(id));
		return "employeeForm";
	}

	@PutMapping("/updateEmployee")
	ModelAndView editEmployee(@RequestBody Employee employee){
		return new ModelAndView("redirect:/employees");
	}

	@DeleteMapping("/employees/delete/{id}")
	ModelAndView deleteEmployee(@PathVariable int id){
		return new ModelAndView("redirect:/employees");
	}
}
