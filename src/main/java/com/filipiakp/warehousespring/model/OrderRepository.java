package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<Order> findById(long id);
}
