package com.mascova.talarion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascova.talarion.domain.Product;

/**
 * Spring Data JPA repository for the Product entity.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
