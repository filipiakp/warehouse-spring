package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Order;
import com.filipiakp.warehousespring.entities.Task;
import com.filipiakp.warehousespring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

  @Autowired private EmployeeRepository employeeRepository;

  @Autowired private ContractorRepository contractorRepository;

  @Autowired private ProductRepository productRepository;

  @Autowired private OrderRepository orderRepository;

  @Autowired private TaskRepository taskRepository;

  @RequestMapping("/")
  public String getHomepage(Model model) {

    List<Order> orders = orderRepository.findAll();
    List<Task> tasks = taskRepository.findAll();
    model.addAttribute("employeesCount", employeeRepository.count());
    model.addAttribute("contractorsCount", contractorRepository.count());
    model.addAttribute("productsCount", productRepository.count());
    model.addAttribute("ordersCount", orders.size());
    model.addAttribute(
        "ordersFinished",
        orders.stream()
            .filter(order -> order.getFinishDate() != null)
            .collect(Collectors.toList()));
    model.addAttribute(
        "ordersInProgress",
        orders.stream()
            .filter(order -> order.getFinishDate() == null)
            .collect(Collectors.toList()));
    Map<String, List<Task>> tasksByImportance =
        tasks.stream().collect(Collectors.groupingBy(t -> String.valueOf(t.getImportance())));
    Map<String, Integer> tasksCountByImportance = new HashMap<>();
    tasksByImportance
        .keySet()
        .forEach(
            tKey -> {
              tasksCountByImportance.put(tKey, tasksByImportance.get(tKey).size());
            });
    model.addAttribute("tasksCountByImportance", tasksCountByImportance);
    Map<String, List<Order>> ordersByDate =
        orders.stream()
            .filter(o -> o.getFinishDate() != null)
            .collect(
                Collectors.groupingBy(
                    o -> new SimpleDateFormat("dd.MM").format(o.getFinishDate())));
    Map<String, Integer> ordersCountByDate = new HashMap<>();
    ordersByDate
        .keySet()
        .forEach(
            tKey -> {
              ordersCountByDate.put(tKey, ordersByDate.get(tKey).size());
            });
    model.addAttribute("ordersCountByDate", ordersCountByDate);
    return "index";
  }

  //	@RequestMapping("/error")
  //	public String getErrorPage(Model model){
  //		return "error";
  //	}

}
