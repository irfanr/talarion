package com.mascova.talarion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascova.talarion.domain.SalesHead;

/**
 * Spring Data JPA repository for the SalesHead entity.
 */
public interface SalesHeadRepository extends JpaRepository<SalesHead, Long> {

}
