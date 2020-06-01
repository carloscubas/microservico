package com.example.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stock.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
