package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Employee;
import com.filipiakp.warehousespring.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository repository;

	@RequestMapping("/employees/add")
	String add(Model model){
		model.addAttribute("employee",new Employee());
		return "employeeForm";
	}

	@RequestMapping(value="/saveEmployee", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method=RequestMethod.POST)
	String saveEmployee(@RequestParam Map<String, String> data){
		Employee employee = data.get("id").equals("0")? new Employee():repository.findById(Integer.parseInt(data.get("id"))).get();
		employee.setName(data.get("name"));
		employee.setSurname(data.get("surname"));
		employee.setApartmentNumber(data.get("apartmentNumber"));
		employee.setCity(data.get("city"));
		employee.setHouseNumber(data.get("houseNumber"));
		employee.setPosition(data.get("position"));
		employee.setSalary(Double.parseDouble(data.get("salary")));
		employee.setStreet(data.get("street"));
		try {
			employee.setEmploymentDate(new SimpleDateFormat("dd.MM.yyyy").parse(data.get("employmentDate")));
		} catch (ParseException e) {
			e.printStackTrace();
			//TODO:make validation
		}
		repository.save(employee);
		return "redirect:/employees";
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

	@RequestMapping("/employees/delete/{id}")
	String deleteEmployee(@PathVariable int id){
		Optional<Employee> employeeOptional = repository.findById(id);
		if (employeeOptional.isPresent()) {
			repository.delete(employeeOptional.get());
		}
		return "redirect:/employees";
	}
}
