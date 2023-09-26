package com.deivinson.gerenciadordespesas.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.tests.Factory;

public class UserTest {

	private User usuario;
	
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
        User usuario = new User(1L, "João");

        String expectedToString = "Usuario(id=1, nome=João, despesas=[])";
        assertThat(usuario.toString()).isEqualTo(expectedToString);
    }
	
	@Test
    public void testEqualsComInstanciasIguais() {
        User usuario1 = new User(1L, "João");
        User usuario2 = new User(1L, "João");

        assertThat(usuario1).isEqualTo(usuario2);
    }

    @Test
    public void testEqualsComInstanciasDiferentes() {
        User usuario1 = new User(1L, "João");
        User usuario2 = new User(2L, "Maria");

        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    public void testHashCodeConsistente() {
        User usuario1 = new User(1L, "João");
        User usuario2 = new User(1L, "João");

        assertThat(usuario1.hashCode()).isEqualTo(usuario2.hashCode());
    }

    @Test
    public void testHashCodeDiferenteParaObjetosDiferentes() {
        User usuario1 = new User(1L, "João");
        User usuario2 = new User(2L, "Maria");

        assertThat(usuario1.hashCode()).isNotEqualTo(usuario2.hashCode());
    }
    
    @Test
    public void testConstrutorComArgumentos() {
        User usuario = new User(1L, "João");

        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo(1L);
        assertThat(usuario.getNome()).isEqualTo("João");
    }
    
    @Test
    public void testEqualsComNull() {
        User usuario = new User(1L, "João");

        assertThat(usuario.equals(null)).isFalse();
    }
    
    @SuppressWarnings("unlikely-arg-type")
	@Test
    public void testEqualsComObjetosDeClassesDiferentes() {
        User usuario = new User(1L, "João");
        Category categoria = new Category();

        assertThat(usuario.equals(categoria)).isFalse();
    }
    
    @Test
    public void testEqualsComInstanciasDiferentesIsFalse() {
        User usuario1 = new User(1L, "João");
        User usuario2 = new User(2L, "Maria");

        assertThat(usuario1.equals(usuario2)).isFalse();
    }
    
}
