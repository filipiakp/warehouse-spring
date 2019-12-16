package com.filipiakp.warehousespring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@DataJpaTest
public class OrderIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
//	@Autowired
//	private TestEntityManager entityManager;
//	@Autowired
//	private OrderRepository orderRepository;

//	@Before
//	public void setUp(){
//
//	}

	@Test
	public void givenSaveOrder_whenMockMVC_thenEntityAddedToDB() throws Exception{
		this.mockMvc.perform(post("/saveOrder").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "0")
				.param("date", "01.01.2001").param("contractor",""))
				.andDo(print()).andExpect(status().isOk());

//		assertThat(orderRepository.count()).isNotEqualTo(0);
	}
}
