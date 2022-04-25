package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Product;
import com.filipiakp.warehousespring.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ProductController {

  @Autowired private ProductRepository repository;

  @RequestMapping("/products/add")
  public String add(Model model) {
    model.addAttribute("product", new Product());
    return "productForm";
  }

  @RequestMapping(
      value = "/saveProduct",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      method = RequestMethod.POST)
  public String saveProduct(@Valid Product data, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "productForm";
    }
    Product product =
        repository.existsById(data.getCode())
            ? repository.findByCode(data.getCode()).get()
            : new Product();
    product.setCode(data.getCode());
    product.setManufacturer(data.getManufacturer());
    product.setName(data.getName());
    product.setSpecification(data.getSpecification());
    product.setAmount(data.getAmount());
    product.setPrice(data.getPrice());
    product.setCategory(data.getCategory());
    product.setWeight(data.getWeight());
    repository.save(product);
    return "redirect:/products";
  }

  @RequestMapping("/products")
  public String getAll(Model model) {
    model.addAttribute("products", repository.findAll());
    return "products";
  }

  @RequestMapping("/products/edit/{code}")
  public String edit(@PathVariable String code, Model model) {
    model.addAttribute("product", repository.findByCode(code));
    return "productForm";
  }

  @RequestMapping("/products/delete/{code}")
  public String deleteProduct(@PathVariable String code) {
    Optional<Product> productOptional = repository.findByCode(code);
    if (productOptional.isPresent()) {
      repository.delete(productOptional.get());
    }
    return "redirect:/products";
  }
}
