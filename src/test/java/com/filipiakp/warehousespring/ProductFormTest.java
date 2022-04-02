package com.filipiakp.warehousespring;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = WarehouseSpringApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:integration-tests.properties")
public class ProductFormTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ProductRepository repository;

  @Autowired private WebApplicationContext wac;

  @Before
  public void setUp() {}

  @Test
  public void givenProductFormController_whenCorrectDataPOSTed_thenEntityAddedToRepository()
      throws Exception {
    long before = repository.count();
    // when
    this.mockMvc
        .perform(
            post("/saveProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "1234567890")
                .param("manufacturer", "Intel")
                .param("name", "Procesor i5 6600K")
                .param("specification", "i5 6600K Skylake 4 rdzenie @3.6GHz")
                .param("amount", "10")
                .param("price", "519.99")
                .param("category", "Procesory")
                .param("weight", "20.789"))
        .andExpect(status().is(302));

    // then
    assertEquals(before + 1, repository.count());
  }
}
