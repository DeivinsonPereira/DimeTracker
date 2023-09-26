package com.deivinson.dimeTracker.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deivinson.dimeTracker.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Optional<Category> findByName(String name);
}
