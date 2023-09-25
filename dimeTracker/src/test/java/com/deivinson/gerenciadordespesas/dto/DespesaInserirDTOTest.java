package com.deivinson.gerenciadordespesas.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;

public class DespesaInserirDTOTest {

	private DespesaInserirDTO despesaInserirDTO;
	
	@BeforeEach
	public void SetUp() {
		
		despesaInserirDTO = new DespesaInserirDTO();
	}
	
	@Test
	public void testGetAndSetId() {
		despesaInserirDTO.setId(1L);
		
		assertEquals(1L, despesaInserirDTO.getId());
	}
	
	@Test
	public void testGetAndSetValor() {
		
		despesaInserirDTO.setValor(100.00);
		
		assertEquals(100.00, despesaInserirDTO.getValor());
	}
	
	@Test
	public void testGetAndSetData() {
		
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		despesaInserirDTO.setData(data);
		
		assertEquals(data, despesaInserirDTO.getData());
		
	}
	
	@Test
	public void testGetAndSetCategoriaId() {
		
		despesaInserirDTO.setCategoriaId(1L);
		
		assertEquals(1L, despesaInserirDTO.getCategoriaId());	
	}
	
	@Test
	public void testGetAndSetUsuarioId() {
		despesaInserirDTO.setUsuarioId(1L);
		
		assertEquals(1L, despesaInserirDTO.getUsuarioId());
	}
	
	@Test
	public void testConstructorEntityToDTOTransformation() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		despesaInserirDTO = new DespesaInserirDTO(1L, 100.00, data, 1L, 1L);
		
		assertEquals(1L, despesaInserirDTO.getId());
		assertEquals(100.00, despesaInserirDTO.getValor());
		assertEquals(data, despesaInserirDTO.getData());
		assertEquals(1L, despesaInserirDTO.getCategoriaId());
		assertEquals(1L, despesaInserirDTO.getUsuarioId());
	}
	
	@Test
    public void testConstrutorComArgumentos() {
        Categoria categoria = new Categoria(1L, "Categoria Teste");

        Usuario usuario = new Usuario(1L, "Usuário Teste");

        Despesa despesa = new Despesa(1L, 1000.0, LocalDate.now(), usuario, categoria);

        DespesaInserirDTO despesaInserirDTO = new DespesaInserirDTO(despesa);

        assertThat(despesaInserirDTO.getId()).isEqualTo(despesa.getId());
        assertThat(despesaInserirDTO.getValor()).isEqualTo(despesa.getValor());
        assertThat(despesaInserirDTO.getData()).isEqualTo(despesa.getData());
        assertThat(despesaInserirDTO.getCategoriaId()).isEqualTo(categoria.getId());
        assertThat(despesaInserirDTO.getUsuarioId()).isEqualTo(usuario.getId());
    }
}
