package com.filipiakp.warehousespring.model;


import com.filipiakp.warehousespring.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
	Optional<OrderProduct> findById(Long id);
}
