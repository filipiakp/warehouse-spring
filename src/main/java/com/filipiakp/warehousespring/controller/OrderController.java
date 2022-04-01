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
	private OrderRepository repository;
	@Autowired
	private ContractorRepository contractorRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderProductRepository orderProductRepository;

	@RequestMapping("/orders/add")
	public String add(Model model){
		model.addAttribute("order",new OrderDTO());
		model.addAttribute("contractors",contractorRepository.findAll());
		model.addAttribute("products", productRepository.findAll());
		return "orderForm";
	}

	@RequestMapping(value="/saveOrder", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method=RequestMethod.POST)
	public String saveOrder(@Valid OrderDTO data){
		Order order = repository.existsById(data.getId())?repository.findById(data.getId()).get():new Order();

		if(data.getContractor()!=null && !data.getContractor().equals(""))
			order.setContractor(contractorRepository.findByNip(data.getContractor()).get());
		order.setFinishDate(data.getFinishDate());


		if(data.getProductsList() != null && data.getProductsList().length != 0) {
			int items = data.getProductsList().length;
			Set<OrderProduct> orderProductSet = new HashSet<>();
			long tempOPId = 0;
			boolean tempOPdeleted = false;
			for (int i = 0; i < items; ++i) {

				tempOPId = data.getProductsList()[i].getId();
				tempOPdeleted = data.getProductsList()[i].isDeleted();

				if (tempOPId != 0 && tempOPdeleted) {
					OrderProduct orderProduct = orderProductRepository.findById(tempOPId).get();
					orderProductSet.remove(orderProduct);
					orderProductRepository.delete(orderProduct);
				} else if (!tempOPdeleted) {
					OrderProduct orderProduct = (tempOPId == 0) ? new OrderProduct() : orderProductRepository.findById(tempOPId).get();
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
	public String getAll(Model model){
		model.addAttribute("orders",repository.findAll());
		return "orders";
	}

	@RequestMapping("/orders/edit/{id}")
	public String edit(@PathVariable long id, Model model){
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
		OrderDTO orderDTO = OrderDTO.builder()
				.id(order.getId())
				.creationDate(order.getCreationDate())
				.finishDate(order.getFinishDate())
				.productsList(opDTOList)
				.contractor((order.getContractor()!=null)?
						(order.getContractor().getNip() +" "+ order.getContractor().getName()):"")
				.build();

		model.addAttribute("order",orderDTO);
		model.addAttribute("contractors",contractorRepository.findAll());

		model.addAttribute("products", productList);
		return "orderForm";
	}

	@RequestMapping("/orders/delete/{id}")
	public String deleteOrder(@PathVariable long id){
		Optional<Order> orderOptional = repository.findById(id);
		if (orderOptional.isPresent()) {
			repository.delete(orderOptional.get());
		}
		return "redirect:/orders";
	}
}
