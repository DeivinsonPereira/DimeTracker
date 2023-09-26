package com.deivinson.gerenciadordespesas.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MinCategoryDTOTest {

	private MinCategoryDTO minCategoriaDTO;
	
	@BeforeEach
	public void SetUp() {
		
		minCategoriaDTO = new MinCategoryDTO();
	}
	
	@Test
	public void testGetAndSetNome() {
		minCategoriaDTO.setNome("Energia");
		
		assertTrue(minCategoriaDTO.getNome().equalsIgnoreCase("Energia"));
	}
	
	@Test
    public void testConstrutorComArgumentos() {
        String nome = "Categoria Teste";

        MinCategoryDTO minCategoriaDTO = new MinCategoryDTO(nome);

        assertThat(minCategoriaDTO.getNome()).isEqualTo(nome);
    }
	
}
