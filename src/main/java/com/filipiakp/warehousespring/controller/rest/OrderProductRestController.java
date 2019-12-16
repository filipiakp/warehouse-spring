package com.filipiakp.warehousespring.controller.rest;

import com.filipiakp.warehousespring.entities.OrderProduct;
import com.filipiakp.warehousespring.model.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-products")
public class OrderProductRestController {

	@Autowired
	private OrderProductRepository orderProductRepository;

	@GetMapping
	public ResponseEntity<List<OrderProduct>> getOrderProducts(){
		return new ResponseEntity<>(orderProductRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderProduct> getOrderProductDetails(@PathVariable Long id){
		return new ResponseEntity<>(orderProductRepository.findById(id).get(),HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<OrderProduct> createOrderProduct(@RequestBody OrderProduct orderProduct){
		orderProductRepository.save(orderProduct);
		return new ResponseEntity<>(orderProduct, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<OrderProduct> updateOrderProduct(@RequestBody OrderProduct newOP){
		OrderProduct oldOP = orderProductRepository.findById(newOP.getId()).get();
		oldOP.setId(newOP.getId());
		oldOP.setProduct(newOP.getProduct());
		oldOP.setQuantity(newOP.getQuantity());
		orderProductRepository.save(oldOP);
		return new ResponseEntity<>(oldOP, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrderProduct(@PathVariable Long id){
		Optional<OrderProduct> orderProductOptional = orderProductRepository.findById(id);
		if (orderProductOptional.isPresent()) {
			orderProductRepository.delete(orderProductOptional.get());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}