package com.deivinson.dimeTracker.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MinCategoryDTOTest {

	private MinCategoryDTO minCategoryDTO;
	
	@BeforeEach
	public void SetUp() {
		
		minCategoryDTO = new MinCategoryDTO();
	}
	
	@Test
	public void testGetAndSetName() {
		minCategoryDTO.setName("Energy");
		
		assertTrue(minCategoryDTO.getName().equalsIgnoreCase("Energy"));
	}
	
	@Test
    public void testConstrutorWithArgs() {
        String name = "Test Category";

        MinCategoryDTO minCategoryDTO = new MinCategoryDTO(name);

        assertThat(minCategoryDTO.getName()).isEqualTo(name);
    }
	
}
