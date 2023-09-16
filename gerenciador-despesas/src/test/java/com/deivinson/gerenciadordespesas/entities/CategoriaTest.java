package com.deivinson.gerenciadordespesas.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoriaTest {
	
	private Categoria categoria;

	@BeforeEach
	public void setUp() {
		categoria = new Categoria();
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
