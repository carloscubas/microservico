package com.example.ecommerce.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.model.Sale;
import com.example.ecommerce.services.CheckoutServices;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

	@Autowired
	private CheckoutServices services;

	@PostMapping("/sale")
	public ResponseEntity<Boolean> sale(@RequestBody Sale sale) {
		services.saleProduct(sale);
		return ResponseEntity.ok().build();
	}

}
