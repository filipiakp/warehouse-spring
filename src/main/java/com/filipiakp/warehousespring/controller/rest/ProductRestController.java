package com.filipiakp.warehousespring.controller.rest;

import com.filipiakp.warehousespring.entities.Product;
import com.filipiakp.warehousespring.model.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

  @Autowired private ProductRepository productRepository;

  @GetMapping
  public ResponseEntity<List<Product>> getProducts() {
    return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{code}")
  public ResponseEntity<Product> getProductDetails(@PathVariable String code) {
    return new ResponseEntity<>(productRepository.findByCode(code).get(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    productRepository.save(product);
    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<Product> updateProduct(@RequestBody Product newProd) {
    Product oldProd = productRepository.findByCode(newProd.getCode()).get();
    oldProd.setManufacturer(newProd.getManufacturer());
    oldProd.setName(newProd.getName());
    oldProd.setSpecification(newProd.getSpecification());
    oldProd.setAmount(newProd.getAmount());
    oldProd.setPrice(newProd.getPrice());
    oldProd.setCategory(newProd.getCategory());
    oldProd.setWeight(newProd.getWeight());
    productRepository.save(oldProd);
    return new ResponseEntity<>(oldProd, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable String code) {
    Optional<Product> productOptional = productRepository.findByCode(code);
    if (productOptional.isPresent()) {
      productRepository.delete(productOptional.get());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
