package com.deivinson.gerenciadordespesas.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.deivinson.gerenciadordespesas.dto.CategoryDTO;
import com.deivinson.gerenciadordespesas.dto.MinCategoryDTO;
import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.repositories.CategoriaRepository;
import com.deivinson.gerenciadordespesas.services.exceptions.DatabaseException;
import com.deivinson.gerenciadordespesas.services.exceptions.InvalidInputException;
import com.deivinson.gerenciadordespesas.services.exceptions.ResourceNotFoundException;
import com.deivinson.gerenciadordespesas.tests.Factory;

@ExtendWith(SpringExtension.class)
public class CategoriaServiceTest {

	@InjectMocks
	private CategoriaService service;

	@Mock
	private CategoriaRepository repository;

	private Category categoria;
	private PageImpl<Category> page;
	private MinCategoryDTO minCategoriaDTO;

	private Long categoriaId;
	private Long categoriaIdInexistente;

	@BeforeEach
	void setUp() throws Exception {
		categoriaId = 1L;
		categoriaIdInexistente = 2L;

		categoria = Factory.construtorCategoriaComArgumentosEDespesa();
		page = new PageImpl<>(List.of(categoria));
		minCategoriaDTO = new MinCategoryDTO();

	}

	@Test
	public void buscarTodasCategoriasShouldReturnPage() {

		Pageable pageable = PageRequest.of(0, 10);

		when(repository.findAll((Pageable) any())).thenReturn(page);

		Page<CategoryDTO> result = service.buscarTodasCategorias(pageable);

		Assertions.assertNotNull(result);
	}

	@Test
	public void buscarTodasCategoriasShouldReturnAllElements() {

		Pageable pageable = PageRequest.of(0, 10);

		Category categoria1 = Factory.construtorCategoriaVazio();
		categoria1.setNome("Categoria 1");

		Category categoria2 = Factory.construtorCategoriaVazio();
		categoria2.setNome("Categoria 2");

		List<Category> categorias = Arrays.asList(categoria1, categoria2);

		Page<Category> paginaCategorias = new PageImpl<>(categorias);

		when(repository.findAll(pageable)).thenReturn(paginaCategorias);

		Page<CategoryDTO> resultado = service.buscarTodasCategorias(pageable);

		assertEquals(categorias.size(), resultado.getContent().size());
	}

	@Test
	public void buscarTodasCategoriasShouldReturnCategoriaDTO() {

		Pageable pageable = PageRequest.of(0, 10);

		Category categoria1 = Factory.construtorCategoriaVazio();
		categoria1.setNome("Categoria 1");

		Category categoria2 = Factory.construtorCategoriaVazio();
		categoria2.setNome("Categoria 2");

		List<Category> categorias = Arrays.asList(categoria1, categoria2);

		Page<Category> paginaCategorias = new PageImpl<>(categorias);

		when(repository.findAll(pageable)).thenReturn(paginaCategorias);

		Page<CategoryDTO> resultado = service.buscarTodasCategorias(pageable);

		List<String> nomes = resultado.getContent().stream().map(CategoryDTO::getNome).collect(Collectors.toList());

		assertTrue(nomes.contains("Categoria 1"));
		assertTrue(nomes.contains("Categoria 2"));
	}

	@Test
	public void buscarTodasCategoriasShouldReturnEmptyWhenwithoutCategorias() {

		Pageable pageable = PageRequest.of(0, 10);

		List<Category> categorias = new ArrayList<>();

		Page<Category> paginaCategorias = new PageImpl<>(categorias);

		when(repository.findAll(pageable)).thenReturn(paginaCategorias);

		Page<CategoryDTO> resultado = service.buscarTodasCategorias(pageable);

		assertTrue(resultado.isEmpty());
	}

	@Test
	public void criarCategoriaShouldCreateNewCategoria() {

		minCategoriaDTO.setNome("Nova Categoria");

		Category categoriaSalva = Factory.construtorCategoriaComArgumentosEDespesa();

		when(repository.save(any(Category.class))).thenReturn(categoriaSalva);

		CategoryDTO resultado = service.criarCategoria(minCategoriaDTO);

		assertNotNull(resultado);
		assertEquals("Energia", resultado.getNome());
		assertEquals(1L, resultado.getId());
	}

	@Test
	public void criarCategoriaShouldThrowInvalidImputExceptionWhenEntradaInvalidImput() {
		minCategoriaDTO.setNome("");
		

		assertThrows(InvalidInputException.class, () -> service.criarCategoria(minCategoriaDTO));
	}

	@Test
	public void criarCategoriaShouldNotCreateWhenConflitoDeNomes() {
		
		minCategoriaDTO.setNome("Categoria Existente");

		Category categoriaExistente = Factory.construtorCategoriaVazio();
		categoriaExistente.setId(categoriaId);
		categoriaExistente.setNome("Categoria Existente");

		when(repository.findByNome("Categoria Existente")).thenReturn(Optional.of(categoriaExistente));

		CategoryDTO result = service.criarCategoria(minCategoriaDTO);
		assertNull(result);
	}

	@Test
	public void criarCategoriaShouldSavaInReposity() {
		
		minCategoriaDTO.setNome("Nova Categoria");

		Category categoriaParaSalvar = Factory.construtorCategoriaVazio();
		categoriaParaSalvar.setNome("Nova Categoria");

		Category categoriaSalva = Factory.construtorCategoriaVazio();
		categoriaSalva.setId(categoriaId);
		categoriaSalva.setNome("Nova Categoria");

		when(repository.save(any(Category.class))).thenReturn(categoriaSalva);

		CategoryDTO resultado = service.criarCategoria(minCategoriaDTO);

		verify(repository, times(1)).save(categoriaParaSalvar);

		assertNotNull(resultado);
		assertEquals("Nova Categoria", resultado.getNome());
		assertEquals(categoriaId, resultado.getId());
	}

	@Test
	public void criarCategoriashouldReturnDTO() {
		
		minCategoriaDTO.setNome("Nova Categoria");

		Category categoriaSalva = new Category();
		categoriaSalva.setId(categoriaId);
		categoriaSalva.setNome("Nova Categoria");

		when(repository.save(any(Category.class))).thenReturn(categoriaSalva);

		CategoryDTO resultado = service.criarCategoria(minCategoriaDTO);

		assertNotNull(resultado);
		assertEquals("Nova Categoria", resultado.getNome());
		assertEquals(categoriaId, resultado.getId());
	}

	@Test
	public void atualizarNomeCategoriaShouldFindCategoriaWhenExistsId() {

		minCategoriaDTO.setNome("Novo Nome");

		Category categoriaExistente = Factory.construtorCategoriaVazio();
		categoriaExistente.setId(categoriaId);
		categoriaExistente.setNome("Nome Antigo");

		when(repository.findById(categoriaId)).thenReturn(Optional.of(categoriaExistente));
		when(repository.save(any(Category.class))).thenReturn(categoriaExistente);

		service.atualizarNomeCategoria(categoriaId, minCategoriaDTO);

		assertEquals("Novo Nome", categoriaExistente.getNome());

	}

	@Test
	public void atualizarNomeCategoriashouldReturnDTO() {
		
		minCategoriaDTO.setNome("Novo Nome");

		Category categoriaExistente = Factory.construtorCategoriaVazio();
		categoriaExistente.setId(categoriaId);
		categoriaExistente.setNome("Nome Antigo");

		when(repository.findById(categoriaId)).thenReturn(Optional.of(categoriaExistente));
		when(repository.save(any(Category.class))).thenReturn(categoriaExistente);

		CategoryDTO resultado = service.atualizarNomeCategoria(categoriaId, minCategoriaDTO);

		assertNotNull(resultado);
		assertEquals("Novo Nome", resultado.getNome());
		assertEquals(categoriaId, resultado.getId());
	}
	
	@Test
    public void atualizarNomeCategoriaShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		
		minCategoriaDTO.setNome("Novo Nome");

        when(repository.findById(categoriaIdInexistente)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.atualizarNomeCategoria(categoriaIdInexistente, minCategoriaDTO));
    }
	
	@Test
	public void atualizarNomeCategoriaShouldKeepSameNameWhenNameIsEqual() {
		
		minCategoriaDTO.setNome("Nome Antigo"); 

	    Category categoriaExistente = new Category();
	    categoriaExistente.setId(categoriaId);
	    categoriaExistente.setNome("Nome Antigo");

	    when(repository.findById(categoriaId)).thenReturn(Optional.of(categoriaExistente));
	    when(repository.save(any(Category.class))).thenReturn(categoriaExistente);

	    CategoryDTO resultado = service.atualizarNomeCategoria(categoriaId, minCategoriaDTO);

	    assertEquals("Nome Antigo", resultado.getNome());
	}
	
	@Test
	public void atualizarNomeCategoriaShouldThrowInvalidInputExceptionWhenInvalidInputValue() {
		
		minCategoriaDTO.setNome("");

	    assertThrows(InvalidInputException.class, () -> service.atualizarNomeCategoria(categoriaId, minCategoriaDTO));
	}
	
	@Test
    public void deletarCategoriaShouldDeleteCategoryWhenCategoriaExists() {

        doNothing().when(repository).deleteById(categoriaId);

        assertDoesNotThrow(() -> service.deletarCategoria(categoriaId));
        
    }
	
	@Test
    public void deletarCategoriaShouldThrowResourceNotFoundExceptionWhenCategoriaNoExists() {

        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(categoriaIdInexistente);

        assertThrows(ResourceNotFoundException.class, () -> service.deletarCategoria(categoriaIdInexistente));
    }
	
	@Test
    public void deletarCategoriaShouldThrowDatabaseExceptionWhenDataIntegrityFailure() {
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(categoriaId);

        assertThrows(DatabaseException.class, () -> service.deletarCategoria(categoriaId));
    }

}