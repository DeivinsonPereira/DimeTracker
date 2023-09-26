package com.deivinson.gerenciadordespesas.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.entities.User;

public class ExpenseDTOTest {

	private ExpenseDTO despesaDTO;
	
	@BeforeEach
	public void SetUp() {
		
		despesaDTO = new ExpenseDTO();
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
		
		despesaDTO = new ExpenseDTO(1L, 100.00, data, "João", "Energia");
		
		
		assertEquals(1L, despesaDTO.getId());
		assertEquals(100.00, despesaDTO.getValor());
		assertEquals(data, despesaDTO.getData());
		assertTrue(despesaDTO.getNomeUsuario().equalsIgnoreCase("João"));
		assertTrue(despesaDTO.getNomeCategoria().equalsIgnoreCase("Energia"));
		
	}
	
	@Test
    public void testConstrutorComArgumentos() {
        Category categoria = new Category(1L, "Categoria Teste");

        User usuario = new User(1L, "Usuário Teste");

        Expense despesa = new Expense(1L, 1000.0, LocalDate.now(), usuario, categoria);

        ExpenseDTO despesaDTO = new ExpenseDTO(despesa);

        assertThat(despesaDTO.getId()).isEqualTo(despesa.getId());
        assertThat(despesaDTO.getValor()).isEqualTo(despesa.getValor());
        assertThat(despesaDTO.getData()).isEqualTo(despesa.getData());
        assertThat(despesaDTO.getNomeUsuario()).isEqualTo(usuario.getNome());
        assertThat(despesaDTO.getNomeCategoria()).isEqualTo(categoria.getNome());
    }
	
	@Test
    public void testConstrutorComArgumentosComNomeUsuarioENomeCategoria() {
        Category categoria = new Category(1L, "Categoria Teste");

        User usuario = new User(1L, "Usuário Teste");

        Expense despesa = new Expense(1L, 1000.0, LocalDate.now(), usuario, categoria);

        ExpenseDTO despesaDTO = new ExpenseDTO(despesa);

        assertThat(despesaDTO.getNomeUsuario()).isEqualTo(usuario.getNome());
        assertThat(despesaDTO.getNomeCategoria()).isEqualTo(categoria.getNome());
    }

    @Test
    public void testConstrutorComArgumentosSemNomeUsuarioENomeCategoria() {
        Expense despesa = new Expense(1L, 1000.0, LocalDate.now(), null, null);

        ExpenseDTO despesaDTO = new ExpenseDTO(despesa);

        assertThat(despesaDTO.getNomeUsuario()).isNull();
        assertThat(despesaDTO.getNomeCategoria()).isNull();
    }
    
    
    @Test
    public void testEqualsAndHashCode() {
        ExpenseDTO despesaDTO1 = new ExpenseDTO(1L, 1000.0, LocalDate.now(), "Usuário", "Categoria");
        ExpenseDTO despesaDTO2 = new ExpenseDTO(1L, 1000.0, LocalDate.now(), "Usuário", "Categoria");

        assertThat(despesaDTO1).isEqualTo(despesaDTO2);

        assertThat(despesaDTO1.hashCode()).isEqualTo(despesaDTO2.hashCode());

        ExpenseDTO despesaDTO3 = new ExpenseDTO(2L, 2000.0, LocalDate.now().plusDays(1), "OutroUsuário", "OutraCategoria");

        assertThat(despesaDTO1).isNotEqualTo(despesaDTO3);

        assertThat(despesaDTO1.hashCode()).isNotEqualTo(despesaDTO3.hashCode());

        assertThat(despesaDTO1.equals(null)).isFalse();

    }
    
    
}
