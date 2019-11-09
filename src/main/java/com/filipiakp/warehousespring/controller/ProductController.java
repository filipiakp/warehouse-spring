package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Product;
import com.filipiakp.warehousespring.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class ProductController {

	@Autowired
	ProductRepository repository;

	@RequestMapping("/products/add")
	String add(Model model){
		model.addAttribute("product",new Product());
		return "productForm";
	}

	@RequestMapping(value="/saveProduct", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method=RequestMethod.POST)
	String saveProduct(@RequestParam Map<String, String> data){
		Product product = repository.existsById(data.get("code"))?repository.findByCode(data.get("code")).get():new Product();
		product.setCode(data.get("code"));
		product.setManufacturer(data.get("manufacturer"));
		product.setName(data.get("name"));
		product.setSpecification(data.get("specification"));
		product.setAmount(Long.parseLong(data.get("amount")));
		product.setPrice(Double.parseDouble(data.get("price")));
		product.setCategory(data.get("category"));
		product.setWeight(Double.parseDouble(data.get("weight")));
		repository.save(product);
		return "redirect:/products";
	}

	@RequestMapping("/products")
	String getAll(Model model){
		model.addAttribute("products",repository.findAll());
		return "products";
	}

	@RequestMapping("/products/edit/{code}")
	String edit(@PathVariable String code, Model model){
		model.addAttribute("product",repository.findByCode(code));
		return "productForm";
	}

	@RequestMapping("/products/delete/{code}")
	String deleteProduct(@PathVariable String code){
		Optional<Product> productOptional = repository.findByCode(code);
		if (productOptional.isPresent()) {
			repository.delete(productOptional.get());
		}
		return "redirect:/products";
	}
}
