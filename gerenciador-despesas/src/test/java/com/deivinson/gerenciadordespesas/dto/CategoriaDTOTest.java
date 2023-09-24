package com.deivinson.gerenciadordespesas.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.entities.Categoria;

public class CategoriaDTOTest {

	
	private CategoriaDTO categoriaDTO;
	
	@BeforeEach
	public void setUp() {
		
		categoriaDTO = new CategoriaDTO();
	}
	
	@Test
	public void testGetAndSetNome(){
		
		categoriaDTO.setNome("João");
		
		assertTrue(categoriaDTO.getNome().equalsIgnoreCase("João"));
	}
	
	@Test
	public void testConstructorWithArguments() {
		
		categoriaDTO = new CategoriaDTO(1L, "João");
		
		assertTrue(categoriaDTO.getNome().equalsIgnoreCase("João"));
		assertEquals(1L,categoriaDTO.getId());
	}
	
	@Test
	public void testConstructorEntityToDTOTransformation() {

		categoriaDTO = new CategoriaDTO(new Categoria(1L, "Energia"));
		
		assertEquals(1L, categoriaDTO.getId());
		assertTrue(categoriaDTO.getNome().equalsIgnoreCase("Energia"));
		
	}
}
