package com.deivinson.gerenciadordespesas.tests;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.deivinson.gerenciadordespesas.dto.ExpenseDTO;
import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.entities.User;

public class Factory {
	
	public static Category emptyConstructorCategory() {
		return new Category();
	}
	
	public static Category constructorCategoryWihArgs() {
		return new Category(1L, "Energy");
	}
	
	public static Category constructorCategorWithArgsAndExpense() {
		LocalDate date = LocalDate.of(2023, 1, 1);
		
		BigDecimal valueExpense = new BigDecimal("100.00");
		
		User user = constructorUserWithArgs();
		
		Category category = new Category(1L, "Energy");
		category.getExpenses().add(new Expense(1L,valueExpense, date, user, category));
		
		return category;
	}
	
	public static User emptyConstructorUser() {
		return new User();
	}
	
	public static User constructorUserWithArgs() {
		return new User(1L, "Joe");
	}
	
	public static User constructorUserWithArgsWithExpense() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		BigDecimal valueExpense = new BigDecimal("100.00");
		
		User user = new User(1L, "Joe");
		
		Category category = new Category(1L, "Energy");
		
		user.getExpenses().add(new Expense(1L,valueExpense, data, user, category));
		
		return user;
	}
	
	public static Expense emptyconstructorExpense() {
		return new Expense();
	}
	
	public static Expense constructorExpenseWithArgs() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		BigDecimal valueExpense = new BigDecimal("100.00");
		
		User user = constructorUserWithArgs();
		
		Category category = constructorCategoryWihArgs();
		
		return new Expense(1L, valueExpense, data, user, category);
	}
	
	public static ExpenseDTO emptyconstructorExpenseDTO() {
		return new ExpenseDTO();
	}
}
