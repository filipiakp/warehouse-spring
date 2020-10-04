package com.filipiakp.warehousespring;

import com.filipiakp.warehousespring.entities.*;
import com.filipiakp.warehousespring.model.*;
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
public class EmployeeFormTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private EmployeeRepository repository;


	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp(){

	}
	@Test
	public void givenEmployeeFormController_whenCorrectDataPOSTed_thenEntityAddedToRepository() throws Exception{
		long before = repository.count();
		//when
		this.mockMvc.perform(post("/saveEmployee").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "0")
				.param("name", "Jan")
				.param("surname", "Nowak")
				.param("position", "Sprzedawca")
				.param("salary", "1700")
				.param("city", "Warszawa")
				.param("street", "al. Jana Pawła 2")
				.param("houseNumber", "222")
				.param("apartmentNumber", "15B")
				.param("employmentDate", "2000-01-01")
			).andExpect(status().is(302));

		//then
		assertEquals(before+1,repository.count());

	}
}
