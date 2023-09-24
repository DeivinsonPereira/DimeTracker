package com.deivinson.gerenciadordespesas.entities;

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
    public void testToString() {
        Despesa despesa = Factory.construtorDespesaComArgumentos();

        String resultadoToString = despesa.toString();

        String saídaEsperada = "Despesa(id=1, valor=100.0, data=2023-01-01, usuario=Usuario(id=1, nome=João, despesas=[]), categoria=Categoria(id=1, nome=Energia, despesas=[]))";

        assertEquals(saídaEsperada, resultadoToString);
    }
}
