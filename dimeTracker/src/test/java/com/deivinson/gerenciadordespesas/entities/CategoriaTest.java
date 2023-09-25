package com.deivinson.gerenciadordespesas.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoriaTest {
	
	private Categoria categoria1;
	private Categoria categoria2;

	@BeforeEach
	public void setUp() {
		categoria1 = new Categoria(1L, "Alimentação");
        categoria2 = new Categoria(2L, "Transporte");
	}
	
	@Test
	public void testGetAndSetId() {
		categoria1.setId(1L);
		assertEquals(1L, categoria1.getId());
	}
	
	@Test
	public void testGetAndSetNome() {
		categoria1.setNome("joão");
		
		assertTrue(categoria1.getNome().equalsIgnoreCase("João"));
	}
	
    @Test
    public void testEqualsComInstanciasIguais() {
        Categoria mesmaCategoria = new Categoria(1L, "Alimentação");
        assertThat(categoria1.equals(mesmaCategoria)).isTrue();
    }

    @Test
    public void testEqualsComInstanciasDiferentes() {
        assertThat(categoria1.equals(categoria2)).isFalse();
    }

    @Test
    public void testEqualsComNull() {
        assertThat(categoria1.equals(null)).isFalse();
    }

    @Test
    public void testEqualsReflexividade() {
        assertThat(categoria1.equals(categoria1)).isTrue();
    }

    @Test
    public void testEqualsSimetria() {
        assertThat(categoria1.equals(categoria2)).isEqualTo(categoria2.equals(categoria1));
    }

    @Test
    public void testEqualsTransitividade() {
        Categoria outraCategoria = new Categoria(1L, "Alimentação");
        assertThat(categoria1.equals(outraCategoria)).isTrue();
        assertThat(outraCategoria.equals(categoria2)).isFalse();
        assertThat(categoria1.equals(categoria2)).isFalse();
    }

    @Test
    public void testHashCodeConsistente() {
        Categoria mesmaCategoria = new Categoria(1L, "Alimentação");
        assertThat(categoria1.hashCode()).isEqualTo(mesmaCategoria.hashCode());
    }

    @Test
    public void testHashCodeDiferenteParaObjetosDiferentes() {
        assertThat(categoria1.hashCode()).isNotEqualTo(categoria2.hashCode());
    }

    @Test
    public void testToString() {
        assertThat(categoria1.toString()).isEqualTo("Categoria(id=1, nome=Alimentação, despesas=[])");
    }
    
    @Test
    public void testConstrutorVazio() {
        Categoria categoria = new Categoria();

        assertThat(categoria).isNotNull();

        assertThat(categoria.getId()).isNull();
        assertThat(categoria.getNome()).isNull();
        assertThat(categoria.getDespesas()).isNotNull().isEmpty();
    }
	
    
    
}
