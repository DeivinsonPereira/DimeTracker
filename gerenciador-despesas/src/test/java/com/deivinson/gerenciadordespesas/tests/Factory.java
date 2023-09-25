package com.deivinson.gerenciadordespesas.tests;

import java.time.LocalDate;

import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;

public class Factory {

	public static Categoria construtorCategoriaVazio() {
		return new Categoria();
	}
	
	public static Categoria construtorCategoriaComArgumentos() {
		return new Categoria(1L, "Energia");
	}
	
	public static Categoria construtorCategoriaComArgumentosEDespesa() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		Usuario usuario = construtorUsuarioComArgumentos();
		
		Categoria categoria = new Categoria(1L, "Energia");
		categoria.getDespesas().add(new Despesa(1L,100.00, data, usuario, categoria));
		
		return categoria;
	}
	
	public static Usuario construtorUsuarioVazio() {
		return new Usuario();
	}
	
	public static Usuario construtorUsuarioComArgumentos() {
		return new Usuario(1L, "João");
	}
	
	public static Usuario construtorUsuarioComArgumentosComDespesa() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		Usuario usuario = new Usuario(1L, "João");
		
		Categoria categoria = new Categoria(1L, "Energia");
		
		usuario.getDespesas().add(new Despesa(1L,100.00, data, usuario, categoria));
		
		return usuario;
	}
	
	public static Despesa construtorDespesaVazio() {
		return new Despesa();
	}
	
	public static Despesa construtorDespesaComArgumentos() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		Usuario usuario = construtorUsuarioComArgumentos();
		
		Categoria categoria = construtorCategoriaComArgumentos();
		
		return new Despesa(1L, 100.00, data, usuario, categoria);
	}
	
	public static DespesaDTO construtorDespesaDTOVazio() {
		return new DespesaDTO();
	}
}
