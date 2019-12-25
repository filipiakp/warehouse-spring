package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Order;
import com.filipiakp.warehousespring.entities.OrderProduct;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
//		OrderDTO orderDTO = new OrderDTO();
//		orderDTO.setContractor("0");
		model.addAttribute("order",new OrderDTO());
		model.addAttribute("contractors",contractorRepository.findAll());
		model.addAttribute("products", productRepository.findAll());
		return "orderForm";
	}

	@RequestMapping(value="/saveOrder", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method=RequestMethod.POST)
	String saveOrder(@RequestParam Map<String,String> data){
		Order order = repository.existsById(Long.parseLong(data.get("id")))?repository.findById(Long.parseLong(data.get("id"))).get():new Order();
		if(data.get("contractor")!=null && !data.get("contractor").equals(""))
			order.setContractor(contractorRepository.findByNip(data.get("contractor")).get());
		try {
			order.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(data.get("date")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//Tutaj MOŻĘ Być w przyszłości problem
		//hardcoded variable can cause problems in the future
		int items = (data.size()-3)/4;
		Set<OrderProduct> orderProductSet = new HashSet<>();
		long tempOPId = 0;
		for (int i=0; i<items;++i){
			OrderProduct orderProduct = new OrderProduct();
			tempOPId = Long.parseLong(data.get("productsList["+i+"].id"));
			if(tempOPId != 0)
				orderProduct.setId(tempOPId);
			orderProduct.setProduct(productRepository.findByCode(data.get("productsList["+i+"].productCode")).get());
			orderProduct.setQuantity(Integer.parseInt(data.get("productsList["+i+"].quantity")));
			orderProductSet.add(orderProduct);
		}
		order.setProductsList(orderProductSet);

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
		Set<OrderProductDTO> opDTOList = new HashSet();
		for(OrderProduct op : order.getProductsList()){
			OrderProductDTO opDTO = new OrderProductDTO();
			opDTO.setId(op.getId());
			opDTO.setProductCode(op.getProduct().getCode());
			opDTO.setProductName(op.getProduct().getName());
			opDTO.setQuantity(op.getQuantity());
			opDTOList.add(opDTO);
		}
		OrderDTO orderDTO = new OrderDTO(order.getId(),order.getDate(),opDTOList,(order.getContractor()!=null)? (order.getContractor().getNip() +" "+ order.getContractor().getName()):"");
		model.addAttribute("order",orderDTO);
		model.addAttribute("contractors",contractorRepository.findAll());//.add(new Contractor("0","Wybierz Kontrahenta","","","","","",false))
		model.addAttribute("products", productRepository.findAll());
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
