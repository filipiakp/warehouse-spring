package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Employee;
import com.filipiakp.warehousespring.model.EmployeeRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmployeeController {

  @Autowired private EmployeeRepository repository;

  @RequestMapping("/employees/add")
  public String add(Model model) {
    model.addAttribute("employee", new Employee());
    return "employeeForm";
  }

  @RequestMapping(
      value = "/saveEmployee",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      method = RequestMethod.POST)
  public String saveEmployee(@Valid Employee data, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "employeeForm";
    }
    Employee employee =
        data.getId() == 0 ? new Employee() : repository.findById(data.getId()).get();
    employee.setName(data.getName());
    employee.setSurname(data.getSurname());
    employee.setApartmentNumber(data.getApartmentNumber());
    employee.setCity(data.getCity());
    employee.setHouseNumber(data.getHouseNumber());
    employee.setPosition(data.getPosition());
    employee.setSalary(data.getSalary());
    employee.setStreet(data.getStreet());
    employee.setEmploymentDate(data.getEmploymentDate());
    repository.save(employee);
    return "redirect:/employees";
  }

  @RequestMapping("/employees")
  public String getAll(Model model) {
    model.addAttribute("employees", repository.findAll());
    return "employees";
  }

  @RequestMapping("/employees/edit/{id}")
  public String edit(@PathVariable int id, Model model) {
    model.addAttribute("employee", repository.findById(id));
    return "employeeForm";
  }

  @RequestMapping("/employees/delete/{id}")
  public String deleteEmployee(@PathVariable int id) {
    Optional<Employee> employeeOptional = repository.findById(id);
    if (employeeOptional.isPresent()) {
      repository.delete(employeeOptional.get());
    }
    return "redirect:/employees";
  }
}
