package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Task;
import com.filipiakp.warehousespring.entities.dto.TaskDTO;
import com.filipiakp.warehousespring.model.ContractorRepository;
import com.filipiakp.warehousespring.model.EmployeeRepository;
import com.filipiakp.warehousespring.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Controller
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping("/tasks/add")
    public String add(Model model){
        TaskDTO task = new TaskDTO();
        task.setCreationDate(new Date(System.currentTimeMillis()));
        model.addAttribute("task", task);
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("contractors", contractorRepository.findAll());
        return "taskForm";
    }

    @RequestMapping(value="/saveTask", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method= RequestMethod.POST)
    public String saveTask(@Valid Task data, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "taskForm";
        }
        Task task = taskRepository.existsById(data.getId())? taskRepository.findById(data.getId()).get():new Task();
        task.setId(data.getId());
        task.setName(data.getName());
        task.setCreationDate(data.getCreationDate());
        task.setContractor(data.getContractor());
        task.setDescription(data.getDescription());
        task.setEmployees(data.getEmployees());
        task.setImportance(data.getImportance());
        task.setFinishedDate(data.getFinishedDate());
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @RequestMapping("/tasks")
    public String getAll(Model model){
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks";
    }

    @RequestMapping("/tasks/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("task", taskRepository.findById(id));
        return "taskForm";
    }

    @RequestMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            taskRepository.delete(taskOptional.get());
        }
        return "redirect:/tasks";
    }
}
