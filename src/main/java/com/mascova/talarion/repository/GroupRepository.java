package com.mascova.talarion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascova.talarion.domain.Group;

/**
 * Spring Data JPA repository for the Group entity.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
}
