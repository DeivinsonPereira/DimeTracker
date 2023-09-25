package com.deivinson.gerenciadordespesas.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;

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
	
	@Test
    public void testConstrutorComArgumentos() {
        Categoria categoria = new Categoria(1L, "Categoria Teste");

        Usuario usuario = new Usuario(1L, "Usuário Teste");

        Despesa despesa = new Despesa(1L, 1000.0, LocalDate.now(), usuario, categoria);

        DespesaDTO despesaDTO = new DespesaDTO(despesa);

        assertThat(despesaDTO.getId()).isEqualTo(despesa.getId());
        assertThat(despesaDTO.getValor()).isEqualTo(despesa.getValor());
        assertThat(despesaDTO.getData()).isEqualTo(despesa.getData());
        assertThat(despesaDTO.getNomeUsuario()).isEqualTo(usuario.getNome());
        assertThat(despesaDTO.getNomeCategoria()).isEqualTo(categoria.getNome());
    }
	
	@Test
    public void testConstrutorComArgumentosComNomeUsuarioENomeCategoria() {
        Categoria categoria = new Categoria(1L, "Categoria Teste");

        Usuario usuario = new Usuario(1L, "Usuário Teste");

        Despesa despesa = new Despesa(1L, 1000.0, LocalDate.now(), usuario, categoria);

        DespesaDTO despesaDTO = new DespesaDTO(despesa);

        assertThat(despesaDTO.getNomeUsuario()).isEqualTo(usuario.getNome());
        assertThat(despesaDTO.getNomeCategoria()).isEqualTo(categoria.getNome());
    }

    @Test
    public void testConstrutorComArgumentosSemNomeUsuarioENomeCategoria() {
        Despesa despesa = new Despesa(1L, 1000.0, LocalDate.now(), null, null);

        DespesaDTO despesaDTO = new DespesaDTO(despesa);

        assertThat(despesaDTO.getNomeUsuario()).isNull();
        assertThat(despesaDTO.getNomeCategoria()).isNull();
    }
    
    
    @Test
    public void testEqualsAndHashCode() {
        DespesaDTO despesaDTO1 = new DespesaDTO(1L, 1000.0, LocalDate.now(), "Usuário", "Categoria");
        DespesaDTO despesaDTO2 = new DespesaDTO(1L, 1000.0, LocalDate.now(), "Usuário", "Categoria");

        assertThat(despesaDTO1).isEqualTo(despesaDTO2);

        assertThat(despesaDTO1.hashCode()).isEqualTo(despesaDTO2.hashCode());

        DespesaDTO despesaDTO3 = new DespesaDTO(2L, 2000.0, LocalDate.now().plusDays(1), "OutroUsuário", "OutraCategoria");

        assertThat(despesaDTO1).isNotEqualTo(despesaDTO3);

        assertThat(despesaDTO1.hashCode()).isNotEqualTo(despesaDTO3.hashCode());

        assertThat(despesaDTO1.equals(null)).isFalse();

    }
    
    
}
