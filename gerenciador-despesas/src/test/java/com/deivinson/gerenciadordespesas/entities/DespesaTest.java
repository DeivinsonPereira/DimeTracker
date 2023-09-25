package com.deivinson.gerenciadordespesas.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.tests.Factory;

public class DespesaTest {

	private Despesa despesa;
	
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
		despesa.setUsuario(new Usuario(1L, "João"));
		
		assertNotNull(despesa.getUsuario());
		assertEquals(1L, despesa.getUsuario().getId());
	}
	
	@Test
	public void testGetAndSetCategoria() {
		despesa.setCategoria(new Categoria(1L, "Energia"));
		
		assertNotNull(despesa.getCategoria());
		assertEquals(1L, despesa.getCategoria().getId());
	}
	
	
	@Test
    public void testEquals() {
        Despesa despesa1 = new Despesa(1L, 100.0, LocalDate.now(), null, null);
        Despesa despesa2 = new Despesa(1L, 200.0, LocalDate.now(), null, null);

        assertThat(despesa1).isEqualTo(despesa2);
    }
	
	@Test
    public void testNotEquals() {
        Despesa despesa1 = new Despesa(1L, 100.0, LocalDate.now(), null, null);
        Despesa despesa2 = new Despesa(2L, 100.0, LocalDate.now(), null, null);

        assertThat(despesa1).isNotEqualTo(despesa2);
    }
	
	@Test
    public void testHashCode() {
        Despesa despesa1 = new Despesa(1L, 100.0, LocalDate.now(), null, null);
        Despesa despesa2 = new Despesa(1L, 200.0, LocalDate.now(), null, null);

        assertThat(despesa1.hashCode()).isEqualTo(despesa2.hashCode());
    }
	
	 @Test
	    public void testIgualdadeComInstanciasIguais() {
	        Despesa despesa1 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Despesa despesa2 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1).isEqualTo(despesa2);
	    }
	 
	 @Test
	    public void testIgualdadeComInstanciasDiferentes() {
	        Despesa despesa1 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Despesa despesa2 = new Despesa(2L, 150.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1).isNotEqualTo(despesa2);
	    }
	 
	 @Test
	    public void testIgualdadeComNull() {
	        Despesa despesa = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa.equals(null)).isFalse();
	    }

	    @Test
	    public void testReflexividade() {
	        Despesa despesa = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa.equals(despesa)).isTrue();
	    }

	    @Test
	    public void testSimetria() {
	        Despesa despesa1 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Despesa despesa2 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1.equals(despesa2)).isEqualTo(despesa2.equals(despesa1));
	    }

	    @Test
	    public void testTransitividade() {
	        Despesa despesa1 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Despesa despesa2 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Despesa despesa3 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1.equals(despesa2)).isTrue();
	        assertThat(despesa2.equals(despesa3)).isTrue();
	        assertThat(despesa1.equals(despesa3)).isTrue();
	    }

	    @SuppressWarnings("unlikely-arg-type")
		@Test
	    public void testIgualdadeComObjetosDeClassesDiferentes() {
	        Despesa despesa = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Categoria categoria = new Categoria();

	        assertThat(despesa.equals(categoria)).isFalse();
	    }
	    
	    @Test
	    public void testConsistenciaAposMudancas() {
	        Despesa despesa1 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);
	        Despesa despesa2 = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(despesa1.equals(despesa2)).isTrue();

	        despesa2.setValor(150.0);

	        assertThat(despesa1.getValor().equals(despesa2.getValor())).isFalse();
	    }
	    
	    @Test
	    public void testToString() {
	        Despesa despesa = new Despesa(1L, 100.0, LocalDate.of(2023, 9, 25),null,null);

	        String toStringResult = despesa.toString();

	        String expectedToString = "Despesa(id=1, valor=100.0, data=2023-09-25, usuario=null, categoria=null)";

	        assertThat(toStringResult).isEqualTo(expectedToString);
	    }
	    
}
