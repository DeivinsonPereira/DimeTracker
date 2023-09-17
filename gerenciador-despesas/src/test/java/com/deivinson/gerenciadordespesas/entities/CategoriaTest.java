package com.deivinson.gerenciadordespesas.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.tests.Factory;

public class CategoriaTest {
	
	private Categoria categoria;

	@BeforeEach
	public void setUp() {
		categoria = Factory.construtorCategoriaVazio();
	}
	
	@Test
	public void testGetAndSetId() {
		categoria.setId(1L);
		assertEquals(1L, categoria.getId());
	}
	
	@Test
	public void testGetAndSetNome() {
		categoria.setNome("joão");
		
		assertTrue(categoria.getNome().equalsIgnoreCase("João"));
	}
	
}
