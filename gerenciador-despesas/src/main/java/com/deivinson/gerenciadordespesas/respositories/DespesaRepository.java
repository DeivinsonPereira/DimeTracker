package com.deivinson.gerenciadordespesas.respositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	List<Despesa> findByCategoria (Categoria categoria); 
	
	@Query("SELECT d "
			+ "FROM Despesa d "
			+ "WHERE d.categoria = :categoria "
			+ "AND d.data BETWEEN :dataInicio "
			+ "AND :dataFim")
	List<Despesa> buscarDespesasPorCategoriaEData(Categoria categoria, LocalDate dataInicio, LocalDate dataFim );

	@Query("SELECT SUM(d.valor) FROM Despesa d ")
	Double calcularDespesaTotal();
	
	@Query("SELECT SUM(d.valor) FROM Despesa d "
			+ "WHERE d.categoria = :categoria")
	Double calcularDespesaTotalPorCategoria(Categoria categoria);
	
	
}
