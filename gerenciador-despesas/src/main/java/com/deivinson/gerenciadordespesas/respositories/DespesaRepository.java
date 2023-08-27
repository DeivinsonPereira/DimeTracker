package com.deivinson.gerenciadordespesas.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deivinson.gerenciadordespesas.entities.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{

}
