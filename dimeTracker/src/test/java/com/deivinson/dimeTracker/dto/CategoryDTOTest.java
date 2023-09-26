package com.deivinson.dimeTracker.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.dimeTracker.entities.Category;

public class CategoryDTOTest {

	
	private CategoryDTO categoryDTO;
	
	@BeforeEach
	public void setUp() {
		
		categoryDTO = new CategoryDTO();
	}
	
	@Test
	public void testGetAndSetName(){
		
		categoryDTO.setName("Joe");
		
		assertTrue(categoryDTO.getName().equalsIgnoreCase("Joe"));
	}
	
	@Test
	public void testConstructorWithArguments() {
		
		categoryDTO = new CategoryDTO(1L, "Joe");
		
		assertTrue(categoryDTO.getName().equalsIgnoreCase("Joe"));
		assertEquals(1L,categoryDTO.getId());
	}
	
	@Test
	public void testConstructorEntityToDTOTransformation() {

		categoryDTO = new CategoryDTO(new Category(1L, "Energy"));
		
		assertEquals(1L, categoryDTO.getId());
		assertTrue(categoryDTO.getName().equalsIgnoreCase("Energy"));
		
	}
	
	@Test
    public void testSetId() {
        CategoryDTO categoryDTO = new CategoryDTO();

        Long id = 123L;
        categoryDTO.setId(id);

        assertThat(categoryDTO.getId()).isEqualTo(id);
    }
}
