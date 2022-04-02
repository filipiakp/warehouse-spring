package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
  Optional<Product> findByCode(String code);
}
