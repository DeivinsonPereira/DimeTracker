package com.deivinson.gerenciadordespesas.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.tests.Factory;

public class UsuarioTest {

	private Usuario usuario;
	
	@BeforeEach
	public void setUp() {
		usuario = Factory.construtorUsuarioVazio();;
	}
	
	@Test
	public void testGetAndSetId() {
		usuario.setId(1L);
		assertEquals(1L, usuario.getId());
	}
	
	@Test
	public void testGetAndSetNome() {
		usuario.setNome("joão");
		
		assertTrue(usuario.getNome().equalsIgnoreCase("João"));
	}
	
	@Test
    public void testToString() {
        Usuario usuario = new Usuario(1L, "João");

        String expectedToString = "Usuario(id=1, nome=João, despesas=[])";
        assertThat(usuario.toString()).isEqualTo(expectedToString);
    }
	
	@Test
    public void testEqualsComInstanciasIguais() {
        Usuario usuario1 = new Usuario(1L, "João");
        Usuario usuario2 = new Usuario(1L, "João");

        assertThat(usuario1).isEqualTo(usuario2);
    }

    @Test
    public void testEqualsComInstanciasDiferentes() {
        Usuario usuario1 = new Usuario(1L, "João");
        Usuario usuario2 = new Usuario(2L, "Maria");

        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    public void testHashCodeConsistente() {
        Usuario usuario1 = new Usuario(1L, "João");
        Usuario usuario2 = new Usuario(1L, "João");

        assertThat(usuario1.hashCode()).isEqualTo(usuario2.hashCode());
    }

    @Test
    public void testHashCodeDiferenteParaObjetosDiferentes() {
        Usuario usuario1 = new Usuario(1L, "João");
        Usuario usuario2 = new Usuario(2L, "Maria");

        assertThat(usuario1.hashCode()).isNotEqualTo(usuario2.hashCode());
    }
    
    @Test
    public void testConstrutorComArgumentos() {
        Usuario usuario = new Usuario(1L, "João");

        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo(1L);
        assertThat(usuario.getNome()).isEqualTo("João");
    }
    
    @Test
    public void testEqualsComNull() {
        Usuario usuario = new Usuario(1L, "João");

        assertThat(usuario.equals(null)).isFalse();
    }
    
    @SuppressWarnings("unlikely-arg-type")
	@Test
    public void testEqualsComObjetosDeClassesDiferentes() {
        Usuario usuario = new Usuario(1L, "João");
        Categoria categoria = new Categoria();

        assertThat(usuario.equals(categoria)).isFalse();
    }
    
    @Test
    public void testEqualsComInstanciasDiferentesIsFalse() {
        Usuario usuario1 = new Usuario(1L, "João");
        Usuario usuario2 = new Usuario(2L, "Maria");

        assertThat(usuario1.equals(usuario2)).isFalse();
    }
    
}
