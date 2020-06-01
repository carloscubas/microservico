package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Sale;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {

}
