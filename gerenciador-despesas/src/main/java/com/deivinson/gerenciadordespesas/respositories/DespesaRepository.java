package com.deivinson.gerenciadordespesas.respositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{

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

	List<Despesa> findByCategoria(Categoria categoriaCascade);
}
