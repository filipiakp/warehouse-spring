package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Employee;
import com.filipiakp.warehousespring.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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
	String saveEmployee(@Valid Employee data, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return "employeeForm";
		}
		Employee employee = data.getId() == 0? new Employee():repository.findById(data.getId()).get();
		employee.setName(data.getName());
		employee.setSurname(data.getSurname());
		employee.setApartmentNumber(data.getApartmentNumber());
		employee.setCity(data.getCity());
		employee.setHouseNumber(data.getHouseNumber());
		employee.setPosition(data.getPosition());
		employee.setSalary(data.getSalary());
		employee.setStreet(data.getStreet());
//		try {
//			employee.setEmploymentDate(new SimpleDateFormat("yyyy-MM-dd").parse(data.get("employmentDate")));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		employee.setEmploymentDate(data.getEmploymentDate());
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
