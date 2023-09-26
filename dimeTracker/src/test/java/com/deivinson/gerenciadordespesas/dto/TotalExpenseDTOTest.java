package com.deivinson.gerenciadordespesas.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TotalExpenseDTOTest {

	private TotalExpenseDTO totalExpenseDTO;
	private BigDecimal valueExpense;
	
	@BeforeEach
	public void SetUp() {
		
		totalExpenseDTO = new TotalExpenseDTO();
		valueExpense = new BigDecimal("100.00");
	}
	
	@Test
	public void testGetAndSetTotalExpense() {
		totalExpenseDTO.setTotalExpenses(valueExpense);
		
		assertEquals(valueExpense, totalExpenseDTO.getTotalExpenses());
	}
	
	@Test
    public void testConstrutorWithArgs() {

        TotalExpenseDTO totalExpenseDTO = new TotalExpenseDTO(valueExpense);

        assertThat(totalExpenseDTO.getTotalExpenses()).isEqualTo(valueExpense);
    }
}
