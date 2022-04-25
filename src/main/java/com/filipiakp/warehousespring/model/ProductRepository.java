package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
  Optional<Product> findByCode(String code);
}
