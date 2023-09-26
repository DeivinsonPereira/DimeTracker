package com.deivinson.gerenciadordespesas.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.entities.Category;

public class CategoryDTOTest {

	
	private CategoryDTO categoriaDTO;
	
	@BeforeEach
	public void setUp() {
		
		categoriaDTO = new CategoryDTO();
	}
	
	@Test
	public void testGetAndSetNome(){
		
		categoriaDTO.setNome("João");
		
		assertTrue(categoriaDTO.getNome().equalsIgnoreCase("João"));
	}
	
	@Test
	public void testConstructorWithArguments() {
		
		categoriaDTO = new CategoryDTO(1L, "João");
		
		assertTrue(categoriaDTO.getNome().equalsIgnoreCase("João"));
		assertEquals(1L,categoriaDTO.getId());
	}
	
	@Test
	public void testConstructorEntityToDTOTransformation() {

		categoriaDTO = new CategoryDTO(new Category(1L, "Energia"));
		
		assertEquals(1L, categoriaDTO.getId());
		assertTrue(categoriaDTO.getNome().equalsIgnoreCase("Energia"));
		
	}
	
	@Test
    public void testSetId() {
        CategoryDTO categoriaDTO = new CategoryDTO();

        Long id = 123L;
        categoriaDTO.setId(id);

        assertThat(categoriaDTO.getId()).isEqualTo(id);
    }
}
