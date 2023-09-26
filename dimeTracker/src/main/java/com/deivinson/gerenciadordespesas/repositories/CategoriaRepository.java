package com.deivinson.gerenciadordespesas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deivinson.gerenciadordespesas.entities.Category;

public interface CategoriaRepository extends JpaRepository<Category, Long>{

	Optional<Category> findByNome(String nome);
}
