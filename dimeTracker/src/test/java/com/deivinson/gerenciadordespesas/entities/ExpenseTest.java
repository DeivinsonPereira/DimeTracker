package com.deivinson.gerenciadordespesas.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.tests.Factory;

public class ExpenseTest {

	private Expense despesa;
	
	@BeforeEach
	public void setUp() {
		despesa = Factory.construtorDespesaVazio();;
	}
	
	@Test
	public void testGetAndSetValor() {
		despesa.setId(1L);
		
		assertEquals(1L, despesa.getId());
	}
	
	@Test
	public void testGetAndSetData() {
		
		LocalDate data = LocalDate.of(2023,01,01);
		
		despesa.setData(data);
		
		assertEquals(data, despesa.getData());
		
	}
	
	@Test
	public void testGetAndSetUsuario() {
		despesa.setUsuario(new User(1L, "João"));
		
		assertNotNull(despesa.getUsuario());
		assertEquals(1L, despesa.getUsuario().getId());
	}
	
	@Test
	public void testGetAndSetCategoria() {
		despesa.setCategoria(new Category(1L, "Energia"));
		
		assertNotNull(despesa.getCategoria());
		assertEquals(1L, despesa.getCategoria().getId());
	}
	
	
	@Test
    public void testEquals() {
        Expense despesa1 = new Expense(1L, 100.0, LocalDate.now(), null, null);
        Expense despesa2 = new Expense(1L, 200.0, LocalDate.now(), null, null);

        assertThat(despesa1).isEqualTo(despesa2);
    }
	
	@Test
    public void testNotEquals() {
        Expense despesa1 = new Expense(1L, 100.0, LocalDate.now(), null, null);
        Expense despesa2 = new Expense(2L, 100.0, LocalDate.now(), null, null);

        assertThat(despesa1).isNotEqualTo(despesa2);
    }
	
	@Test
    public void testHashCode() {
        Expense despesa1 = new Expense(1L, 100.0, LocalDate.now(), null, null);
        Expense despesa2 = new Expense(1L, 200.0, LocalDate.now(), null, null);

        assertThat(despesa1.hashCode()).isEqualTo(despesa2.hashCode());
    }
	
	 @Test
	    public void testIgualdadeComInstanciasIguais() {
	        Expense despesa1 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Expense despesa2 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1).isEqualTo(despesa2);
	    }
	 
	 @Test
	    public void testIgualdadeComInstanciasDiferentes() {
	        Expense despesa1 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Expense despesa2 = new Expense(2L, 150.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1).isNotEqualTo(despesa2);
	    }
	 
	 @Test
	    public void testIgualdadeComNull() {
	        Expense despesa = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa.equals(null)).isFalse();
	    }

	    @Test
	    public void testReflexividade() {
	        Expense despesa = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa.equals(despesa)).isTrue();
	    }

	    @Test
	    public void testSimetria() {
	        Expense despesa1 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Expense despesa2 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1.equals(despesa2)).isEqualTo(despesa2.equals(despesa1));
	    }

	    @Test
	    public void testTransitividade() {
	        Expense despesa1 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Expense despesa2 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Expense despesa3 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1.equals(despesa2)).isTrue();
	        assertThat(despesa2.equals(despesa3)).isTrue();
	        assertThat(despesa1.equals(despesa3)).isTrue();
	    }

	    @SuppressWarnings("unlikely-arg-type")
		@Test
	    public void testIgualdadeComObjetosDeClassesDiferentes() {
	        Expense despesa = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Category categoria = new Category();

	        assertThat(despesa.equals(categoria)).isFalse();
	    }
	    
	    @Test
	    public void testConsistenciaAposMudancas() {
	        Expense despesa1 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Expense despesa2 = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1.equals(despesa2)).isTrue();

	        despesa2.setValor(150.0);

	        assertThat(despesa1.getValor().equals(despesa2.getValor())).isFalse();
	    }
	    
	    @Test
	    public void testToString() {
	        Expense despesa = new Expense(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        String toStringResult = despesa.toString();

	        String expectedToString = "Despesa(id=1, valor=100.0, data=2023-09-25, usuario=null, categoria=null)";

	        assertThat(toStringResult).isEqualTo(expectedToString);
	    }
	    
}
