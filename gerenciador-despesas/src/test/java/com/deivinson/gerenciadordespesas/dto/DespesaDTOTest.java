package com.deivinson.gerenciadordespesas.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DespesaDTOTest {

	private DespesaDTO despesaDTO;
	
	@BeforeEach
	public void SetUp() {
		
		despesaDTO = new DespesaDTO();
	}
	
	@Test
	public void testGetAndSetId() {
		despesaDTO.setId(1L);
		
		assertEquals(1L, despesaDTO.getId());
	}
	
	@Test
	public void testGetAndSetValor() {
		despesaDTO.setValor(100.00);
		
		assertEquals(100.00, despesaDTO.getValor());
	}
	
	@Test
	public void testGetAndSetData() {
		
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		despesaDTO.setData(data);
		
		assertEquals(data, despesaDTO.getData());
	}
	
	@Test
	public void testGetAndSetUsuario() {
		despesaDTO.setNomeUsuario("João");
		
		assertTrue(despesaDTO.getNomeUsuario().equalsIgnoreCase("João"));
	}
	
	@Test
	public void testGetAndSetCategoria() {
		despesaDTO.setNomeCategoria("Energia");
		
		assertTrue(despesaDTO.getNomeCategoria().equalsIgnoreCase("Energia"));
	}
	
	@Test
	public void testConstructorEntityToDTOTransformation() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		despesaDTO = new DespesaDTO(1L, 100.00, data, "João", "Energia");
		
		
		assertEquals(1L, despesaDTO.getId());
		assertEquals(100.00, despesaDTO.getValor());
		assertEquals(data, despesaDTO.getData());
		assertTrue(despesaDTO.getNomeUsuario().equalsIgnoreCase("João"));
		assertTrue(despesaDTO.getNomeCategoria().equalsIgnoreCase("Energia"));
		
	}
}
