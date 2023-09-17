package com.deivinson.gerenciadordespesas.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MinCategoriaDTOTest {

	private MinCategoriaDTO minCategoriaDTO;
	
	@BeforeEach
	public void SetUp() {
		
		minCategoriaDTO = new MinCategoriaDTO();
	}
	
	@Test
	public void testGetAndSetNome() {
		minCategoriaDTO.setNome("Energia");
		
		assertTrue(minCategoriaDTO.getNome().equalsIgnoreCase("Energia"));
	}
	
}
