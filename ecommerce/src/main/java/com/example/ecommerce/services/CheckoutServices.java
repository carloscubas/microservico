package com.example.ecommerce.services;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce.configuration.RabbitConfigure;
import com.example.ecommerce.model.Sale;
import com.example.ecommerce.repository.SalesRepository;


@Service
public class CheckoutServices {
	
	private static final String URL = "http://localhost:8090/stock/updateStock";
	
	 @Autowired
	 private RabbitTemplate rabbitTemplate;

	@Autowired
	SalesRepository salesRepository;
	
	@Autowired
	public RestTemplate restTemplate;
	
	public List<Sale> getAllSales(){
		return salesRepository.findAll();
	}
	
	public Long saleProduct(final Sale sale) {
		Long id = salesRepository.save(sale).getId();
		sendHistoryToStockByRabbt(sale);
		return id;
	}
	
	/*
	 * Atualiza estoque via fila de maneira assincrona
	 */
	private void sendHistoryToStockByRabbt(final Sale sale) {
		rabbitTemplate.convertAndSend(RabbitConfigure.SALE_EX, "", sale);
	}

	/*
	 * Atualiza estoque via serviço rest de maneira sincrona
	 */
	private void sendHistoryToStock(final Sale sale) {
		restTemplate.postForObject(URL, sale, Sale.class);
	}
	
}
