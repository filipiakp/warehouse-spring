package com.filipiakp.warehousespring;

import com.filipiakp.warehousespring.entities.Contractor;
import com.filipiakp.warehousespring.entities.Order;
import com.filipiakp.warehousespring.entities.OrderProduct;
import com.filipiakp.warehousespring.entities.Product;
import com.filipiakp.warehousespring.model.ContractorRepository;
import com.filipiakp.warehousespring.model.OrderProductRepository;
import com.filipiakp.warehousespring.model.OrderRepository;
import com.filipiakp.warehousespring.model.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WarehouseSpringApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:integration-tests.properties")
public class OrderIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ContractorRepository contractorRepository;
	@Autowired
	private OrderProductRepository orderProductRepository;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		Product p1 = new Product();
		p1.setCode("abc");
		p1.setName("Monitor");
		p1.setManufacturer("BrandMonitor");
		p1.setCategory("PC Monitors");
		p1.setWeight(0.5);
		p1.setPrice(450.99);
		p1.setAmount(100);
		p1.setSpecification("Some short monitor specification with \n new line, \t tab and polish characters ążźćńłóęś");
		productRepository.save(p1);

		Product p2 = new Product();
		p2.setCode("def");
		p2.setName("CPU");
		p2.setManufacturer("BrandCPU");
		p2.setCategory("Processors");
		p2.setWeight(0.1);
		p2.setPrice(799.00);
		p2.setAmount(80);
		p2.setSpecification("Some short cpu specification ążźćńłóęś.");
		productRepository.save(p2);

		Contractor c1 = new Contractor();
		c1.setNip("1234567890");
		c1.setName("HurtExPol");
		c1.setPhoneNumber("098765432");
		c1.setSupplier(true);
		c1.setStreet("Wall");
		c1.setHouseNumber("6");
		c1.setApartmentNumber("");
		c1.setCity("Warsaw");
		contractorRepository.save(c1);
	}

	@Test
	public void givenSaveOrderMapping_whenNewOrderPOSTed_thenEntityAddedToRepository() throws Exception{
		long before = orderRepository.count();
		this.mockMvc.perform(post("/saveOrder").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "0")
				.param("date", "2001-01-01")
				.param("contractor","1234567890"))//must not be null
				.andDo(print()).andExpect(status().is(302));//302 because of redirection, not needed

		assertEquals(before+1,orderRepository.count());
	}

	@Test
	public void givenOrderAtRepositoryAndSaveOrderMapping_whenEditedOrderWithProdListPOSTed_thenEntitiesChangedAtRepositories() throws Exception {
		//given
		orderRepository.deleteAll();
		orderProductRepository.deleteAll();
		Order order = new Order();
		order.setId(1);
		order.setContractor(contractorRepository.findByNip("1234567890").get());
		order.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2003-03-03"));

		Set<OrderProduct> orderProducts = new HashSet<OrderProduct>();
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setId(1);
		orderProduct.setQuantity(654);
		orderProduct.setProduct(productRepository.findByCode("abc").get());
		orderProducts.add(orderProduct);

		order.setProductsList(orderProducts);
		order = orderRepository.save(order);
		//orderProduct = orderProductRepository.save(orderProduct);



		//when
		this.mockMvc.perform(post("/saveOrder").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", String.valueOf(order.getId()))
				.param("date", "2002-02-10")
				.param("contractor","1234567890")
				.param("productsList[0].id",String.valueOf(order.getProductsList().stream().findFirst().get().getId()))
				.param("productsList[0].productCode","abc")
				.param("productsList[0].productName","Monitor")
				.param("productsList[0].quantity","654")
				.param("productsList[0].deleted", "true"))
				.andDo(print());

		//then
		Order orderFound = orderRepository.findById(1).get();
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2002-02-10"),orderFound.getDate());//order.getDate()
		assertEquals("1234567890",orderFound.getContractor().getNip());//order.getContractor().getNip());
		assertEquals(0,orderFound.getProductsList().size());//order.getProductsList().size());
	}

	@Test
	@Transactional//Because of org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.filipiakp.warehousespring.entities.Order.productsList, could not initialize proxy - no Session
	public void givenSaveOrderMapping_whenNewOrderWithProdListPOSTed_thenEntitiesFoundAtRepositories() throws Exception {
		orderRepository.deleteAll();

		this.mockMvc.perform(post("/saveOrder").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "0")
				.param("date", "2002-02-02")
				.param("contractor","1234567890")
				.param("productsList[0].id","0")
				.param("productsList[0].productCode","abc")
				.param("productsList[0].productName","Monitor")
				.param("productsList[0].quantity","1")
				.param("productsList[0].deleted", "false"))
				.andDo(print());

		Order orderFound = orderRepository.findAll().get(0);
		OrderProduct orderProductFound = (OrderProduct) orderFound.getProductsList().toArray()[0];


		assertEquals("1234567890",orderFound.getContractor().getNip());
		assertEquals(1,orderFound.getProductsList().size());

		assertEquals(productRepository.findByCode("abc").get(),orderProductFound.getProduct());
		assertEquals(1,orderProductFound.getQuantity());
	}


	@Test
	public void givenOrderWithOrderProducts_whenOrderDeleted_thenOrderProductsDeleted() throws ParseException {
		//given
		orderRepository.deleteAll();
		orderProductRepository.deleteAll();
		Order order = new Order();
		order.setId(0);
		order.setContractor(contractorRepository.findByNip("1234567890").get());
		order.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2003-03-03"));

		Set<OrderProduct> orderProducts = new HashSet<OrderProduct>();
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setId(0);
		orderProduct.setQuantity(654);
		orderProduct.setProduct(productRepository.findByCode("abc").get());
		orderProducts.add(orderProduct);

		order.setProductsList(orderProducts);
		order = orderRepository.save(order);
		orderProductRepository.saveAll(order.getProductsList());
		assertEquals(1,orderProductRepository.count());
		assertEquals(1,orderRepository.count());
		//when
		orderRepository.delete(order);

		//then
		assertEquals(0,orderProductRepository.count());
		assertEquals(0,orderRepository.count());
	}
}
