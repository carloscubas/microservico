package com.example.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.example.stock.models.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Sql(value = PathConfig.CLEAN, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = { PathConfig.DATA })
class StockControllerTest extends FunctionalBaseTest{
	
    private static final String ENDPOINT_STOCK_SAVE = "/stock/save";
    private static final String ENDPOINT_STOCK_LIST = "/stock/list";
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Mock
    private RestTemplate restTemplate;

    MockMvc mvc;
    
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
    void createProductTest() throws Exception {
    	
    	Product product = new Product();
    	product.setDescription("Caneca de chopp transparente");
    	product.setName("Caneca de chopp");
    	product.setPrice(10.0);
    	product.setStock(10);
        
        String requestJson = objectMapper.writeValueAsString(product);
        mvc.perform(post(ENDPOINT_STOCK_SAVE)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void listAllProductTest() throws Exception {
    	
    	MvcResult result = mvc.perform(get(ENDPOINT_STOCK_LIST)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    	
    	String content = result.getResponse().getContentAsString();
    	
    	List<Product> sales = objectMapper.readValue(content, new TypeReference<List<Product>>(){});
    	
    	assertEquals(sales.size(), 4);
    	assertEquals(sales.get(0).getName(), "fone de ouvido");
    	assertEquals(sales.get(1).getName(), "teclado");
    	assertEquals(sales.get(2).getName(), "mouse");
    	assertEquals(sales.get(3).getName(), "monitor");
    }

}