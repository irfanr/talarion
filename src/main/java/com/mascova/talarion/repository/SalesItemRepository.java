package com.mascova.talarion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascova.talarion.domain.SalesItem;

/**
 * Spring Data JPA repository for the SalesHead entity.
 */
public interface SalesItemRepository extends JpaRepository<SalesItem, Long> {

}
