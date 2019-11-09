package com.filipiakp.warehousespring.controller.rest;

import com.filipiakp.warehousespring.entities.Order;
import com.filipiakp.warehousespring.model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

	@Autowired
	private OrderRepository orderRepository;

	@GetMapping
	public ResponseEntity<List<Order>> getPurchaseOrders(){
		return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getPurchaseOrderDetails(@PathVariable long id){
		return new ResponseEntity<>(orderRepository.findById(id).get(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Order> createPurchaseOrder(@RequestBody Order order){
		orderRepository.save(order);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Order> updatePurchaseOrder(@RequestBody Order newPurchOrd){
		Order oldPurchOrd = orderRepository.findById(newPurchOrd.getId()).get();
		oldPurchOrd.setDate(newPurchOrd.getDate());
		oldPurchOrd.setProductsList(newPurchOrd.getProductsList());
		oldPurchOrd.setContractor(newPurchOrd.getContractor());
		orderRepository.save(oldPurchOrd);
		return new ResponseEntity<>(oldPurchOrd, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePurchaseOrder(@PathVariable long id){
		Optional<Order> purchaseOrderOptional = orderRepository.findById(id);
		if (purchaseOrderOptional.isPresent()) {
			orderRepository.delete(purchaseOrderOptional.get());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
