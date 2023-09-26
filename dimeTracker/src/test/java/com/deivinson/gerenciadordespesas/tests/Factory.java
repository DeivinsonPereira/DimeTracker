package com.deivinson.gerenciadordespesas.tests;

import java.time.LocalDate;

import com.deivinson.gerenciadordespesas.dto.ExpenseDTO;
import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.entities.User;

public class Factory {

	public static Category construtorCategoriaVazio() {
		return new Category();
	}
	
	public static Category construtorCategoriaComArgumentos() {
		return new Category(1L, "Energia");
	}
	
	public static Category construtorCategoriaComArgumentosEDespesa() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		User usuario = construtorUsuarioComArgumentos();
		
		Category categoria = new Category(1L, "Energia");
		categoria.getDespesas().add(new Expense(1L,100.00, data, usuario, categoria));
		
		return categoria;
	}
	
	public static User construtorUsuarioVazio() {
		return new User();
	}
	
	public static User construtorUsuarioComArgumentos() {
		return new User(1L, "João");
	}
	
	public static User construtorUsuarioComArgumentosComDespesa() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		User usuario = new User(1L, "João");
		
		Category categoria = new Category(1L, "Energia");
		
		usuario.getDespesas().add(new Expense(1L,100.00, data, usuario, categoria));
		
		return usuario;
	}
	
	public static Expense construtorDespesaVazio() {
		return new Expense();
	}
	
	public static Expense construtorDespesaComArgumentos() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		User usuario = construtorUsuarioComArgumentos();
		
		Category categoria = construtorCategoriaComArgumentos();
		
		return new Expense(1L, 100.00, data, usuario, categoria);
	}
	
	public static ExpenseDTO construtorDespesaDTOVazio() {
		return new ExpenseDTO();
	}
}
