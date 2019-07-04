package com.filipiakp.warehousespring.controller.rest;

import com.filipiakp.warehousespring.entities.Employee;
import com.filipiakp.warehousespring.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping
	public ResponseEntity<List<Employee>> getEmployees(){
		return new ResponseEntity<>(employeeRepository.findAll(),HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeDetails(@PathVariable int id){
		return new ResponseEntity<>(employeeRepository.findById(id).get(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
		employeeRepository.save(employee);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee newEmp){
		Employee oldEmp = employeeRepository.findById(newEmp.getId()).get();
		oldEmp.setName(newEmp.getName());
		oldEmp.setSurname(newEmp.getSurname());
		oldEmp.setApartmentNumber(newEmp.getApartmentNumber());
		oldEmp.setCity(newEmp.getCity());
		oldEmp.setEmploymentDate(newEmp.getEmploymentDate());
		oldEmp.setHouseNumber(newEmp.getHouseNumber());
		oldEmp.setPosition(newEmp.getPosition());
		oldEmp.setSalary(newEmp.getSalary());
		oldEmp.setStreet(newEmp.getStreet());
		employeeRepository.save(oldEmp);
		return new ResponseEntity<>(oldEmp, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id){
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if (employeeOptional.isPresent()) {
			employeeRepository.delete(employeeOptional.get());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
