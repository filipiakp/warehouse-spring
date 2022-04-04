package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Order;
import com.filipiakp.warehousespring.entities.OrderProduct;
import com.filipiakp.warehousespring.entities.Product;
import com.filipiakp.warehousespring.entities.dto.OrderDTO;
import com.filipiakp.warehousespring.entities.dto.OrderProductDTO;
import com.filipiakp.warehousespring.entities.dto.OrderSummaryDTO;
import com.filipiakp.warehousespring.model.ContractorRepository;
import com.filipiakp.warehousespring.model.OrderProductRepository;
import com.filipiakp.warehousespring.model.OrderRepository;
import com.filipiakp.warehousespring.model.ProductRepository;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {

  @Autowired private OrderRepository repository;
  @Autowired private ContractorRepository contractorRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private OrderProductRepository orderProductRepository;

  @RequestMapping("/orders/add")
  public String add(Model model) {
    model.addAttribute("order", new OrderDTO());
    model.addAttribute("contractors", contractorRepository.findAll());
    model.addAttribute(
        "products",
        productRepository.findAll().stream()
            .filter(p -> p.getAmount() > 0)
            .collect(Collectors.toList()));
    return "orderForm";
  }

  @RequestMapping(
      value = "/saveOrder",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      method = RequestMethod.POST)
  public String saveOrder(@Valid OrderDTO data, BindingResult bindingResult, Model model) {
    Order order =
        repository.existsById(data.getId()) ? repository.findById(data.getId()).get() : new Order();

    if (data.getContractor() != null && !data.getContractor().equals(""))
      order.setContractor(contractorRepository.findByNip(data.getContractor()).get());

    if (order.getCreationDate() == null) order.setCreationDate(data.getCreationDate());
    order.setFinishDate(data.getFinishDate());

    if (data.getProductsList() != null && data.getProductsList().length != 0) {
      Set<OrderProduct> orderProductSet = order.getProductsList();
      for (OrderProductDTO dataOP : data.getProductsList()) {

        long tempOPId = dataOP.getId();
        boolean tempOPdeleted = dataOP.isDeleted();

        if (tempOPId != 0 && tempOPdeleted) {
          OrderProduct orderProduct = orderProductRepository.findById(tempOPId).get();
          orderProductSet.remove(orderProduct);
          Optional<Product> repositoryProduct =
              productRepository.findByCode(orderProduct.getProduct().getCode());
          if (repositoryProduct.isPresent()) {
            repositoryProduct
                .get()
                .setAmount(repositoryProduct.get().getAmount() + orderProduct.getQuantity());
            productRepository.save(repositoryProduct.get());
          }
          orderProductRepository.delete(orderProduct);
        } else if (!tempOPdeleted && tempOPId == 0) {
          OrderProduct orderProduct =
              new OrderProduct()
                  .builder()
                  .product(productRepository.findByCode(dataOP.getProductCode()).get())
                  .quantity(dataOP.getQuantity())
                  .build();

          Optional<Product> repositoryProduct =
              productRepository.findByCode(orderProduct.getProduct().getCode());
          long changedAmount = repositoryProduct.get().getAmount() - orderProduct.getQuantity();
          if (changedAmount < 0) {
            model.addAttribute("order", order);
            model.addAttribute("contractors", contractorRepository.findAll());
            model.addAttribute("products", productRepository.findAll());
            bindingResult.addError(
                new ObjectError(
                    "productsList",
                    "Quantity of product " + orderProduct.getProduct().getName() + " exceeded."));
            return "orderForm";
          }
          repositoryProduct.get().setAmount(changedAmount);
          productRepository.save(repositoryProduct.get());

          orderProductSet.add(orderProduct);
        }
      }
    }

    repository.save(order);
    orderProductRepository.saveAll(order.getProductsList());

    return "redirect:/orders";
  }

  @RequestMapping("/orders")
  public String getAll(Model model) {
    List<Order> orders = repository.findAll();
    List<OrderSummaryDTO> orderSummaryDTOList = new LinkedList<OrderSummaryDTO>();
    for (Order o : orders) {
      OrderSummaryDTO osd = new OrderSummaryDTO();
      osd.setId(o.getId());
      osd.setCreationDate(o.getCreationDate());
      osd.setFinishDate(o.getFinishDate());
      osd.setContractor(o.getContractor());
      osd.setSummaryValue(
          o.getProductsList().stream()
              .mapToDouble(obj -> obj.getProduct().getPrice() * obj.getQuantity())
              .sum());
      orderSummaryDTOList.add(osd);
    }
    model.addAttribute("orders", orderSummaryDTOList);
    return "orders";
  }

  @RequestMapping("/orders/edit/{id}")
  public String edit(@PathVariable long id, Model model) {
    Order order = repository.findById(id).get();
    // Set<OrderProductDTO> opDTOList = new HashSet();
    OrderProductDTO[] opDTOList = new OrderProductDTO[order.getProductsList().size()];
    List<Product> productList = productRepository.findAll();
    int i = 0;
    for (OrderProduct op : order.getProductsList()) {
      OrderProductDTO opDTO = new OrderProductDTO();
      opDTO.setId(op.getId());
      opDTO.setProductCode(op.getProduct().getCode());
      opDTO.setProductName(op.getProduct().getName());
      opDTO.setProductPrice(op.getProduct().getPrice());
      opDTO.setQuantity(op.getQuantity());
      opDTO.setDeleted(false);
      opDTOList[i++] = opDTO;
    }
    OrderDTO orderDTO =
        OrderDTO.builder()
            .id(order.getId())
            .creationDate(order.getCreationDate())
            .finishDate(order.getFinishDate())
            .productsList(opDTOList)
            .contractor(
                (order.getContractor() != null)
                    ? (order.getContractor().getNip() + " " + order.getContractor().getName())
                    : "")
            .build();

    model.addAttribute("order", orderDTO);
    model.addAttribute("contractors", contractorRepository.findAll());

    model.addAttribute("products", productList);
    return "orderForm";
  }

  @RequestMapping("/orders/delete/{id}")
  public String deleteOrder(@PathVariable long id) {
    Optional<Order> orderOptional = repository.findById(id);
    if (orderOptional.isPresent()) {
      repository.delete(orderOptional.get());
    }
    return "redirect:/orders";
  }
}
