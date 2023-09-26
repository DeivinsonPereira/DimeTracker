package com.deivinson.dimeTracker.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateExpenseDTOTest {

	private UpdateExpenseDTO updateExpenseDTO;
	private BigDecimal valueExpense;
	
	@BeforeEach
	public void setUp() {
		updateExpenseDTO = new UpdateExpenseDTO();
		valueExpense = new BigDecimal("100.00");
	}
	
	@Test
	public void testGetAndSetValue() {
		updateExpenseDTO.setValueExpense(valueExpense);
		assertEquals(valueExpense, updateExpenseDTO.getValueExpense());
	}
	
	@Test
	public void testGetAndSetDate() {
		LocalDate date = LocalDate.of(2023, 1, 1);
		
		updateExpenseDTO.setDate(date);
		
		assertEquals(date,updateExpenseDTO.getDate());
	}
	
	@Test
	public void testGetAndSetCategoryId() {
		updateExpenseDTO.setCategoryId(1L);
		
		assertEquals(1L, updateExpenseDTO.getCategoryId());
	}
	
	@Test
	public void TestConstructorWithArguments() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		UpdateExpenseDTO constructorArguments = new UpdateExpenseDTO(valueExpense, data, 1L);
		
		assertEquals(valueExpense, constructorArguments.getValueExpense());
		assertEquals(data, constructorArguments.getDate());
		assertEquals(1L, constructorArguments.getCategoryId());
	}
}
