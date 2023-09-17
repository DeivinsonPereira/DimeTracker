package com.deivinson.gerenciadordespesas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deivinson.gerenciadordespesas.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
