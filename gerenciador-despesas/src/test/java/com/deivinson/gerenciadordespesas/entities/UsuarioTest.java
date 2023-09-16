package com.deivinson.gerenciadordespesas.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioTest {

	private Usuario usuario;
	
	@BeforeEach
	public void setUp() {
		usuario = new Usuario();
	}
	
	@Test
	public void testGetAndSetId() {
		usuario.setId(1L);
		assertEquals(1L, usuario.getId());
	}
	
	@Test
	public void testGetAndSetNome() {
		usuario.setNome("joão");
		
		assertTrue(usuario.getNome().equalsIgnoreCase("João"));
	}
}
