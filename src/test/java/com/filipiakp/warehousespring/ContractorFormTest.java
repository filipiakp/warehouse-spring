package com.filipiakp.warehousespring;

import com.filipiakp.warehousespring.model.ContractorRepository;
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
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WarehouseSpringApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:integration-tests.properties")
public class ContractorFormTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ContractorRepository repository;


	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp(){

	}
	@Test
	public void givenContractorFormController_whenCorrectDataPOSTed_thenEntityAddedToRepository() throws Exception{
		long before = repository.count();
		//when
		this.mockMvc.perform(post("/saveContractor").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("nip", "1234567890")
				.param("name", "Jan Nowak Electronix Sp. z o.o.")
				.param("phoneNumber", "123456789")
				.param("city", "Warszawa")
				.param("street", "al. Jana Pawła 2")
				.param("houseNumber", "222")
				.param("apartmentNumber", "")
				.param("employmentDate", "2000-01-01")
				.param("supplier","true")
			).andExpect(status().is(302));

		//then
		assertNotNull(repository.findByNip("1234567890"));

	}
}
