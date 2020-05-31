package com.example.ecommerce.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public ResponseEntity<String> index() {
		return new ResponseEntity<>("Ecommerce", OK);
	}

}
