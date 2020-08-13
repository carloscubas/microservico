package com.example.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import com.example.ecommerce.model.Sale;
import com.example.ecommerce.services.CheckoutServices;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.mockito.Mockito.when;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@Sql(value = PathConfig.CLEAN, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = { PathConfig.DATA })
class CheckoutControllerTest extends FunctionalBaseTest {
	
    private static final String ENDPOINT_SALE = "/checkout/sale";

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mvc;

    @MockBean
	private RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
	
    @Test
    void selfTest () {
    	assertEquals(true, true);
    }
    
    @Test
    void createSaleTest() throws Exception {
    	
        Sale sale = new Sale();
        sale.setIdProduct(1l);
        sale.setAmount(10);
        
        String requestJson = objectMapper.writeValueAsString(sale);
        mvc.perform(post(ENDPOINT_SALE)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void listAllSaleTest() throws Exception {
    	    
    	MvcResult result = mvc.perform(get(ENDPOINT_SALE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    	
    	String content = result.getResponse().getContentAsString();
    	
    	List<Sale> sales = objectMapper.readValue(content, new TypeReference<List<Sale>>(){});
    	
    	assertEquals(sales.size(), 2);
    	assertEquals(sales.get(0).getId(), 1l);
    	assertEquals(sales.get(1).getId(), 2l);
    }
    
}
