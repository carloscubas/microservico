package com.example.stock.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.stock.configuration.RabbitConfigure;
import com.example.stock.models.Product;
import com.example.stock.models.Sale;
import com.example.stock.repository.ProductRepository;

@Service
public class StockService {
	
	Logger logger = LoggerFactory.getLogger(StockService.class);

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	Environment environment;

	public Long save(Product product) {
		return productRepository.save(product).getId();
	}

	public List<Product> getProducts() {
		logger.info("StockService.getProducts= " + environment.getProperty("local.server.port"));
		return productRepository.findAll();
	}

	public Product getProduct(Long idProduct) {
		return productRepository.findById(idProduct).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
	}

	/*
	public void updateStock(Sale sale) {
		Product product = getProduct(sale.getIdProduct());
		product.setStock(product.getStock() - sale.getAmount());
		save(product);
	}
	*/

	@RabbitListener(queues = RabbitConfigure.SALE_QUEUE)
	public void consumer(Sale sale) {
		Product product = getProduct(sale.getIdProduct());
		product.setStock(product.getStock() - sale.getAmount());
		save(product);
	}

}
