package com.mascova.talarion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mascova.talarion.domain.Image;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ImageRepository extends JpaRepository<Image, Long>,
    JpaSpecificationExecutor<Image> {
}
