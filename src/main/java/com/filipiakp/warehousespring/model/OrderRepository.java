package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findById(long id);
}
