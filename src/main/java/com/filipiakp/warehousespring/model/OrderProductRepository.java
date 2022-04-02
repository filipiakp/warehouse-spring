package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.OrderProduct;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
  Optional<OrderProduct> findById(Long id);
}
