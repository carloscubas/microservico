package com.example.ecommerce.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce.configuration.RabbitConfigure;
import com.example.ecommerce.model.Sale;
import com.example.ecommerce.repository.SalesRepository;

@Service
public class CheckoutServices {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutServices.class);
	
	private static final String URL = "http://localhost:8090/stock/updateStock";
	
	 @Autowired
	 private RabbitTemplate rabbitTemplate;

	@Autowired
	SalesRepository salesRepository;
	
	@Autowired
	public RestTemplate restTemplate;

	public void saleProduct(final Sale sale) {
		salesRepository.save(sale);
		sendHistoryToStockByRabbt(sale);
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
