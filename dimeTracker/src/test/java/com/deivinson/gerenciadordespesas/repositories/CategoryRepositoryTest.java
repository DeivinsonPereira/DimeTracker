package com.deivinson.gerenciadordespesas.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.tests.Factory;

@DataJpaTest
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoriaRepository;
	
	@Autowired
	private ExpenseRepository despesaRepository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalCategorias;
	
	@BeforeEach
	void setUp() {
		
		existingId = 1L;
		nonExistingId = 999L;
		countTotalCategorias = 4L;
	}
	
	@Test
	public void testSaveCategoria() {
		
		Category categoria = Factory.construtorCategoriaVazio();
		categoria.setId(existingId);
		categoria.setNome("Energia");
		
		categoriaRepository.save(categoria);
		
		Category categoriaSalva = categoriaRepository.findById(categoria.getId()).orElse(null);
		
		assertNotNull(categoriaSalva);
		assertEquals(existingId, categoriaSalva.getId());
		assertEquals(categoria, categoriaSalva);
		assertTrue(categoriaSalva.getNome().equalsIgnoreCase("Energia"));
	}
	
	@Test
	public void testFindCategoriaById() {
		
		Category categoria = Factory.construtorCategoriaVazio();
		categoria.setNome("Teste");
		categoriaRepository.save(categoria);
		
		Long categoriaId = categoria.getId();
		Category categoriaEncontrada = categoriaRepository.findById(categoriaId).orElse(null);
		
		assertNotNull(categoriaEncontrada);
		assertEquals(categoriaId, categoriaEncontrada.getId());
		assertEquals("Teste", categoriaEncontrada.getNome());
	}

	@Test
	public void testFindCategoriaByIdNotFound() {
		
		Category categoriaEncontrada = categoriaRepository.findById(nonExistingId).orElse(null);
		
		assertNull(categoriaEncontrada);
	}
	
	@Test
	public void testFindAllCategoria() {
		
		Category categoria1 = Factory.construtorCategoriaVazio();
        categoria1.setNome("Categoria 1");

        Category categoria2 = Factory.construtorCategoriaVazio();
        categoria2.setNome("Categoria 2");
        
        Category categoria3 = Factory.construtorCategoriaVazio();
        categoria3.setNome("Categoria 3");
        
        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);
        categoriaRepository.save(categoria3);
        
        List<Category> todasAsCategorias = categoriaRepository.findAll();
        
        assertFalse(todasAsCategorias.isEmpty());
        assertEquals(countTotalCategorias + 3, todasAsCategorias.size());
        
        assertTrue(todasAsCategorias.stream().anyMatch(c -> c.getNome().equals("Categoria 1")));
        assertTrue(todasAsCategorias.stream().anyMatch(c -> c.getNome().equals("Categoria 2")));
        assertTrue(todasAsCategorias.stream().anyMatch(c -> c.getNome().equals("Categoria 3")));
        
	}
	
	@Test
	public void testUpdateCategoria(){
		
		Category categoria = categoriaRepository.findById(1L).orElse(null);
		
		categoria.setId(1L);
		categoria.setNome("João");
		
		categoriaRepository.save(categoria);
		
		assertEquals(1L, categoria.getId());
		assertTrue(categoria.getNome().equalsIgnoreCase("João"));
		
		categoria.setId(35L);
		categoria.setNome("Natasha");
		
		categoriaRepository.save(categoria);
		assertNotEquals(1L, categoria.getId());
		assertFalse(categoria.getNome().equalsIgnoreCase("João"));
		assertEquals(35L, categoria.getId());
		assertTrue(categoria.getNome().equalsIgnoreCase("Natasha"));
		
	}
	
	@Test
	public void deleteCategoria() {
		Category categoria = Factory.construtorCategoriaComArgumentos();
		
		assertEquals(1L, categoria.getId());
		assertTrue(categoria.getNome().equalsIgnoreCase("Energia"));
		
		categoriaRepository.deleteById(1L);
		
		Optional<Category> result = categoriaRepository.findById(existingId);
		
		assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

		Category categoria = new Category();
		categoria.setId(null);
		
		categoria = categoriaRepository.save(categoria);
		
		assertNotNull(categoria);
		assertEquals(countTotalCategorias + 1L, categoria.getId());
	}
	
	@Test
	public void OneToManyRelationshipCategoriaForDespesa () {
		Category categoria1 = Factory.construtorCategoriaComArgumentosEDespesa();
		
		categoriaRepository.save(categoria1);
		
		Category categoriaRelacao = categoriaRepository.findById(categoria1.getId()).orElse(null); 
		assertNotNull(categoriaRelacao);
		assertEquals(1, categoriaRelacao.getDespesas().size());
		
		//Verificando se a relação biderecional está funcionando.
		for(Expense despesa : categoria1.getDespesas()) {
			assertEquals(categoriaRelacao, despesa.getCategoria());
		}
	}
	
	@Test
	public void testCascadeRemoval() {
		Category categoria1 = Factory.construtorCategoriaComArgumentosEDespesa();
		
		categoriaRepository.save(categoria1);
		
		Category categoriaCascade = categoriaRepository.findById(categoria1.getId()).orElse(null); 
		assertNotNull(categoriaCascade);
		assertEquals(1, categoriaCascade.getDespesas().size());
		
		categoriaRepository.delete(categoriaCascade);
		
		Category categoriaRemovida = categoriaRepository.findById(categoriaCascade.getId()).orElse(null);
		
	    assertNull(categoriaRemovida);
	    
	    Optional <Expense> despesa = despesaRepository.findById(categoria1.getId());
	    
	    assertTrue(despesa.isEmpty());
	}
	
}
