package com.deivinson.gerenciadordespesas.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TotalDespesaDTOTest {

	private TotalDespesaDTO totalDespesaDTO;
	
	@BeforeEach
	public void SetUp() {
		
		totalDespesaDTO = new TotalDespesaDTO();
	}
	
	@Test
	public void testGetAndSetTotalDespesa() {
		totalDespesaDTO.setTotalDespesas(100.00);
		
		assertEquals(100.00, totalDespesaDTO.getTotalDespesas());
	}
}
