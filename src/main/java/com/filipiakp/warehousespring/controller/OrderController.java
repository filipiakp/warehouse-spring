package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Order;
import com.filipiakp.warehousespring.entities.OrderProduct;
import com.filipiakp.warehousespring.entities.Product;
import com.filipiakp.warehousespring.entities.dto.OrderDTO;
import com.filipiakp.warehousespring.entities.dto.OrderProductDTO;
import com.filipiakp.warehousespring.model.ContractorRepository;
import com.filipiakp.warehousespring.model.OrderProductRepository;
import com.filipiakp.warehousespring.model.OrderRepository;
import com.filipiakp.warehousespring.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController {

	@Autowired
	OrderRepository repository;
	@Autowired
	ContractorRepository contractorRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	OrderProductRepository orderProductRepository;

	@RequestMapping("/orders/add")
	String add(Model model){
		model.addAttribute("order",new OrderDTO());
		model.addAttribute("contractors",contractorRepository.findAll());
		model.addAttribute("products", productRepository.findAll());
		return "orderForm";
	}

	@RequestMapping(value="/saveOrder", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method=RequestMethod.POST)
	String saveOrder(@Valid OrderDTO data){
		Order order = repository.existsById(data.getId())?repository.findById(data.getId()).get():new Order();

		if(data.getContractor()!=null )//&& !data.getContractor().equals("")
			order.setContractor(contractorRepository.findByNip(data.getContractor()).get());
		order.setDate(data.getDate());

		//Tutaj MOŻĘ Być w przyszłości problem
		//hardcoded variable can cause problems in the future
		//final int numberOfOrderFields = Order.class.getFields().length;
		final int numberOfOrderFields = 4-1;//Id + date + contractor + collection of OrderProducts = 4 - collection
		final int numberOfOPDTOFields = 5;//id + prodCode + prodName + quantity + deleted = 5
		//int items = (data.size() - numberOfOrderFields) /numberOfOPDTOFields;
		if(data.getProductsList() != null && data.getProductsList().length != 0) {
			int items = data.getProductsList().length;
			Set<OrderProduct> orderProductSet = new HashSet<>();
			long tempOPId = 0;
			boolean tempOPdeleted = false;
			for (int i = 0; i < items; ++i) {

				//tempOPId = Long.parseLong(data.get("productsList["+i+"].id"));
				//tempOPdeleted = Boolean.parseBoolean(data.get("productsList["+i+"].deleted"));
				tempOPId = data.getProductsList()[i].getId();
				tempOPdeleted = data.getProductsList()[i].isDeleted();

				if (tempOPId != 0 && tempOPdeleted) {
					OrderProduct orderProduct = orderProductRepository.findById(tempOPId).get();
					orderProductSet.remove(orderProduct);
					orderProductRepository.delete(orderProduct);
				} else if (!tempOPdeleted) {
					OrderProduct orderProduct = (tempOPId == 0) ? new OrderProduct() : orderProductRepository.findById(tempOPId).get();
//				orderProduct.setProduct(productRepository.findByCode(data.get("productsList["+i+"].productCode")).get());
//				orderProduct.setQuantity(Integer.parseInt(data.get("productsList["+i+"].quantity")));
					orderProduct.setProduct(productRepository.findByCode(data.getProductsList()[i].getProductCode()).get());
					orderProduct.setQuantity(data.getProductsList()[i].getQuantity());
					orderProductSet.add(orderProduct);
				}
			}
			order.setProductsList(orderProductSet);
		}

		order = repository.save(order);
		orderProductRepository.saveAll(order.getProductsList());
		return "redirect:/orders";
	}

	@RequestMapping("/orders")
	String getAll(Model model){
		model.addAttribute("orders",repository.findAll());
		return "orders";
	}

	@RequestMapping("/orders/edit/{id}")
	String edit(@PathVariable long id, Model model){
		Order order = repository.findById(id).get();
		//Set<OrderProductDTO> opDTOList = new HashSet();
		OrderProductDTO[] opDTOList = new OrderProductDTO[order.getProductsList().size()];
		List<Product> productList = productRepository.findAll();
		int i = 0;
		for(OrderProduct op : order.getProductsList()){
			OrderProductDTO opDTO = new OrderProductDTO();
			opDTO.setId(op.getId());
			opDTO.setProductCode(op.getProduct().getCode());
			opDTO.setProductName(op.getProduct().getName());
			opDTO.setQuantity(op.getQuantity());
			opDTO.setDeleted(false);
			opDTOList[i++] = opDTO;
		}
		OrderDTO orderDTO = new OrderDTO(order.getId(),order.getDate(),opDTOList,(order.getContractor()!=null)? (order.getContractor().getNip() +" "+ order.getContractor().getName()):"");
		model.addAttribute("order",orderDTO);
		model.addAttribute("contractors",contractorRepository.findAll());

		model.addAttribute("products", productList);
		return "orderForm";
	}

	@RequestMapping("/orders/delete/{id}")
	String deleteOrder(@PathVariable long id){
		Optional<Order> orderOptional = repository.findById(id);
		if (orderOptional.isPresent()) {
			repository.delete(orderOptional.get());
		}
		return "redirect:/orders";
	}
}
