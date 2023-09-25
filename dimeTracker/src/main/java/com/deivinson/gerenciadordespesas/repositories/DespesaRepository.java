package com.deivinson.gerenciadordespesas.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	Page<Despesa> findByCategoriaIdAndDataBetween(Long categoriaId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
	
	Page<Despesa> findByCategoriaId(Long categoriaId, Pageable pageable);
	
	@Query(value = "SELECT d FROM Despesa d "
			+ "LEFT JOIN FETCH d.categoria "
			+ "LEFT JOIN FETCH d.usuario "
			+ "WHERE d.data "
			+ "BETWEEN :dataInicio "
			+ "AND :dataFim",
		       countQuery = "SELECT COUNT(d) FROM Despesa d "
		       		+ "WHERE d.data "
		       		+ "BETWEEN :dataInicio "
		       		+ "AND :dataFim")
	Page<Despesa> findByDataBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim, Pageable pageable);
	
	@Query(value = "SELECT d FROM Despesa d "
			+ "LEFT JOIN FETCH d.categoria "
			+ "LEFT JOIN FETCH d.usuario",
		       countQuery = "SELECT COUNT(d) FROM Despesa d")
	Page<Despesa> findAllWithCategoria(Pageable pageable);
	
	@Query("SELECT SUM(d.valor) "
			+ "FROM Despesa d ")
	Double calcularDespesaTotal();
	
	@Query("SELECT SUM(d.valor) "
			+ "FROM Despesa d "
			+ "WHERE d.categoria = :categoria")
	Double calcularDespesaTotalPorCategoria(Categoria categoria);
	
	@Query("SELECT SUM(d.valor) "
			+ "FROM Despesa d "
			+ "WHERE d.categoria = :categoria "
			+ "AND d.data "
			+ "BETWEEN :dataInicio "
			+ "AND :dataFim")
	Double calcularValorTotalDespesasPorCategoriaEData(Categoria categoria, LocalDate dataInicio, LocalDate dataFim);	
	
	@Query("SELECT COALESCE(SUM(d.valor), 0.0) "
			+ "FROM Despesa d "
			+ "WHERE d.data "
			+ "BETWEEN :dataInicio "
			+ "AND :dataFim")
    Double calcularSomaTotalDespesasPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

}
