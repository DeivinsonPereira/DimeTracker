package com.deivinson.gerenciadordespesas.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

	Page<Expense> findByCategoryIdAndDateBetween(Long categoryId, LocalDate startDate, LocalDate finishDate, Pageable pageable);
	
	Page<Expense> findByCategoryId(Long categoryId, Pageable pageable);
	
	@Query(value = "SELECT d FROM Expense d "
			+ "LEFT JOIN FETCH d.category "
			+ "LEFT JOIN FETCH d.user "
			+ "WHERE d.date "
			+ "BETWEEN :startDate "
			+ "AND :finishDate",
		       countQuery = "SELECT COUNT(d) FROM Expense d "
		       		+ "WHERE d.date "
		       		+ "BETWEEN :startDate "
		       		+ "AND :finishDate")
	Page<Expense> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("finishDate") LocalDate finishDate, Pageable pageable);
	
	@Query(value = "SELECT d FROM Expense d "
			+ "LEFT JOIN FETCH d.category "
			+ "LEFT JOIN FETCH d.user",
		       countQuery = "SELECT COUNT(d) FROM Expense d")
	Page<Expense> findAllWithCategory(Pageable pageable);
	
	@Query("SELECT SUM(d.value) "
			+ "FROM Expense d ")
	BigDecimal calculateTotalExpense();
	
	@Query("SELECT SUM(d.value) "
			+ "FROM Expense d "
			+ "WHERE d.category = :category")
	BigDecimal calculateTotalExpenseByCategory(Category category);
	
	@Query("SELECT SUM(d.value) "
			+ "FROM Expense d "
			+ "WHERE d.category = :category "
			+ "AND d.date "
			+ "BETWEEN :startDate "
			+ "AND :finishDate")
	BigDecimal calculateTotalExpensesByCategoryAndDate(Category category, LocalDate startDate, LocalDate finishDate);	
	
	@Query("SELECT COALESCE(SUM(d.value), 0.0) "
			+ "FROM Expense d "
			+ "WHERE d.date "
			+ "BETWEEN :startDate "
			+ "AND :finishDate")
	BigDecimal calculateTotalExpensesByPeriod(@Param("startDate") LocalDate startDate, @Param("finishDate") LocalDate finishDate);

}
