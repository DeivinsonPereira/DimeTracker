package com.deivinson.gerenciadordespesas.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateExpenseDTOTest {

	private UpdateExpenseDTO atualizarDespesaDTO;
	
	@BeforeEach
	public void setUp() {
		atualizarDespesaDTO = new UpdateExpenseDTO();
	}
	
	@Test
	public void testGetAndSetValor() {
		atualizarDespesaDTO.setValor(100.00);
		assertEquals(100.00, atualizarDespesaDTO.getValor());
	}
	
	@Test
	public void testGetAndSetData() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		atualizarDespesaDTO.setData(data);
		
		assertEquals(data,atualizarDespesaDTO.getData());
	}
	
	@Test
	public void testGetAndSetCategoriaId() {
		atualizarDespesaDTO.setCategoriaId(1L);
		
		assertEquals(1L, atualizarDespesaDTO.getCategoriaId());
	}
	
	@Test
	public void TestConstructorWithArguments() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		UpdateExpenseDTO constructorArguments = new UpdateExpenseDTO(10.0, data, 1L);
		
		assertEquals(10.0, constructorArguments.getValor());
		assertEquals(data, constructorArguments.getData());
		assertEquals(1L, constructorArguments.getCategoriaId());
	}
}
