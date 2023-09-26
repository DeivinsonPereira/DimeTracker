package com.deivinson.gerenciadordespesas.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TotalExpenseDTOTest {

	private TotalExpenseDTO totalDespesaDTO;
	
	@BeforeEach
	public void SetUp() {
		
		totalDespesaDTO = new TotalExpenseDTO();
	}
	
	@Test
	public void testGetAndSetTotalDespesa() {
		totalDespesaDTO.setTotalDespesas(100.00);
		
		assertEquals(100.00, totalDespesaDTO.getTotalDespesas());
	}
	
	@Test
    public void testConstrutorComArgumentos() {
        Double totalDespesas = 1000.0;

        TotalExpenseDTO totalDespesaDTO = new TotalExpenseDTO(totalDespesas);

        assertThat(totalDespesaDTO.getTotalDespesas()).isEqualTo(totalDespesas);
    }
}
