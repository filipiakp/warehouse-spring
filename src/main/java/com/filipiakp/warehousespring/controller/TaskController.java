package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Employee;
import com.filipiakp.warehousespring.entities.Task;
import com.filipiakp.warehousespring.entities.dto.TaskDTO;
import com.filipiakp.warehousespring.model.ContractorRepository;
import com.filipiakp.warehousespring.model.EmployeeRepository;
import com.filipiakp.warehousespring.model.TaskRepository;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
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
public class TaskController {
  @Autowired private TaskRepository taskRepository;

  @Autowired private ContractorRepository contractorRepository;

  @Autowired private EmployeeRepository employeeRepository;

  @RequestMapping("/tasks/add")
  public String add(Model model) {
    TaskDTO task = new TaskDTO();
    task.setCreationDate(new Date(System.currentTimeMillis()));
    model.addAttribute("task", task);
    model.addAttribute("employees", employeeRepository.findAll());
    model.addAttribute("contractors", contractorRepository.findAll());
    return "taskForm";
  }

  @RequestMapping(
      value = "/saveTask",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      method = RequestMethod.POST)
  public String saveTask(@Valid TaskDTO data, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "taskForm";
    }
    Task task =
        taskRepository.existsById(data.getId())
            ? taskRepository.findById(data.getId()).get()
            : new Task();

    if (data.getContractor() != null && !data.getContractor().equals(""))
      task.setContractor(contractorRepository.findByNip(data.getContractor()).get());
    if (task.getCreationDate() == null) task.setCreationDate(data.getCreationDate());
    if (data.getEmployeesList() != null && data.getEmployeesList().length != 0) {
      Set<Employee> employeeSet = task.getEmployees();
      employeeSet.removeIf(
          existingEmployee ->
              Arrays.stream(data.getEmployeesList())
                  .noneMatch(dataEmployeeId -> existingEmployee.getId() == dataEmployeeId));
      for (int employeeId : data.getEmployeesList()) {
        if (employeeSet.stream().anyMatch(e -> e.getId() == employeeId)) continue;
        Optional<Employee> repositoryEmployee = employeeRepository.findById(employeeId);
        if (repositoryEmployee.isPresent()) employeeSet.add(repositoryEmployee.get());
      }
    }
    task.setName(data.getName());
    task.setDescription(data.getDescription());
    task.setImportance(data.getImportance());
    task.setFinishDate(data.getFinishDate());
    taskRepository.save(task);
    return "redirect:/tasks";
  }

  @RequestMapping("/tasks")
  public String getAll(Model model) {
    model.addAttribute("tasks", taskRepository.findAll());
    return "tasks";
  }

  @RequestMapping("/tasks/edit/{id}")
  public String edit(@PathVariable Long id, Model model) {
    Optional<Task> taskOptional = taskRepository.findById(id);
    if (!taskOptional.isPresent()) {
      return "redirect:/tasks/add";
    }
    Task task = taskOptional.get();
    model.addAttribute(
        "task",
        new TaskDTO()
            .builder()
            .id(task.getId())
            .name(task.getName())
            .contractor(
                task.getContractor() != null
                    ? task.getContractor().getNip() + " " + task.getContractor().getName()
                    : "")
            .creationDate(task.getCreationDate())
            .finishDate(task.getFinishDate())
            .description(task.getDescription())
            .employeesList(task.getEmployees().stream().mapToInt(Employee::getId).toArray())
            .importance(task.getImportance())
            .build());
    model.addAttribute("contractors", contractorRepository.findAll());
    model.addAttribute("employees", employeeRepository.findAll());

    return "taskForm";
  }

  @RequestMapping("/tasks/delete/{id}")
  public String deleteTask(@PathVariable Long id) {
    Optional<Task> taskOptional = taskRepository.findById(id);
    if (taskOptional.isPresent()) {
      taskRepository.delete(taskOptional.get());
    }
    return "redirect:/tasks";
  }
}
