package com.deivinson.gerenciadordespesas.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;

public class CategoriaDTOTest {

	
	private CategoriaDTO categoriaDTO;
	
	@BeforeEach
	public void setUp() {
		categoriaDTO = new CategoriaDTO();
	}
	
	@Test
	public void testGetAndSetNome(){
		categoriaDTO.setNome("João");
		
		assertTrue(categoriaDTO.getNome().equalsIgnoreCase("João"));
	}
	
	@Test
	public void testGetDespesas(){
		
		assertNotNull(categoriaDTO.getDespesas());
	}
	
	@Test
	public void testConstructorWithArguments() {
		categoriaDTO = new CategoriaDTO(1L, "João");
		
		assertTrue(categoriaDTO.getNome().equalsIgnoreCase("João"));
		assertEquals(1L,categoriaDTO.getId());
	}
	
	@Test
	public void testConstructorEntityToDTOTransformation() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		Usuario usuario = new Usuario(1L, "João");
		
		Categoria categoria = new Categoria(1L, "Energia");
		
		List<Despesa> despesas = Arrays.asList(new Despesa(1L, 100.00, data, usuario, categoria));
		
		categoriaDTO = new CategoriaDTO(new Categoria(1L, "Energia"), despesas);
		
		
		assertEquals(1L, categoriaDTO.getId());
		assertTrue(categoriaDTO.getNome().equalsIgnoreCase("Energia"));
		assertEquals(1L, categoriaDTO.getDespesas().get(0).getId());
		assertEquals(100.00, categoriaDTO.getDespesas().get(0).getValor());
		assertEquals(data, categoriaDTO.getDespesas().get(0).getData());
		assertTrue(categoriaDTO.getDespesas().get(0).getNomeUsuario().equalsIgnoreCase("João"));
		assertTrue(categoriaDTO.getDespesas().get(0).getNomeCategoria().equalsIgnoreCase("Energia"));
		
	}
}
