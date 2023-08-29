package com.deivinson.gerenciadordespesas.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	List<DespesaDTO> findByCategoria (Categoria categoria); 
	
	@Query("SELECT SUM(d.valor) FROM Despesa d")
	Double calcularDespesaTotal();
}
