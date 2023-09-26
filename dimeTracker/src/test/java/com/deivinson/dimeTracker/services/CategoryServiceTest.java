package com.deivinson.dimeTracker.services;

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

import com.deivinson.dimeTracker.dto.CategoryDTO;
import com.deivinson.dimeTracker.dto.MinCategoryDTO;
import com.deivinson.dimeTracker.entities.Category;
import com.deivinson.dimeTracker.repositories.CategoryRepository;
import com.deivinson.dimeTracker.services.exceptions.DatabaseException;
import com.deivinson.dimeTracker.services.exceptions.InvalidInputException;
import com.deivinson.dimeTracker.services.exceptions.ResourceNotFoundException;
import com.deivinson.dimeTracker.tests.Factory;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

	@InjectMocks
	private CategoryService service;

	@Mock
	private CategoryRepository repository;

	private Category category;
	private PageImpl<Category> page;
	private MinCategoryDTO minCategoryDTO;

	private Long categoryId;
	private Long nonExistentCategoryId;

	@BeforeEach
	void setUp() throws Exception {
		categoryId = 1L;
		nonExistentCategoryId = 2L;

		category = Factory.constructorCategoryWithArgsAndExpense();
		page = new PageImpl<>(List.of(category));
		minCategoryDTO = new MinCategoryDTO();

	}

	@Test
	public void searchAllCategoriesShouldReturnPage() {

		Pageable pageable = PageRequest.of(0, 10);

		when(repository.findAll((Pageable) any())).thenReturn(page);

		Page<CategoryDTO> result = service.searchAllCategories(pageable);

		Assertions.assertNotNull(result);
	}

	@Test
	public void searchAllCategoriesShouldReturnAllElements() {

		Pageable pageable = PageRequest.of(0, 10);

		Category category1 = Factory.emptyConstructorCategory();
		category1.setName("Category 1");

		Category category2 = Factory.emptyConstructorCategory();
		category2.setName("Category 2");

		List<Category> category = Arrays.asList(category1, category2);

		Page<Category> categoriesPage = new PageImpl<>(category);

		when(repository.findAll(pageable)).thenReturn(categoriesPage);

		Page<CategoryDTO> result = service.searchAllCategories(pageable);

		assertEquals(category.size(), result.getContent().size());
	}

	@Test
	public void searchAllCategoriesShouldReturnCategoryDTO() {

		Pageable pageable = PageRequest.of(0, 10);

		Category category1 = Factory.emptyConstructorCategory();
		category1.setName("Category 1");

		Category category2 = Factory.emptyConstructorCategory();
		category2.setName("Category 2");

		List<Category> category = Arrays.asList(category1, category2);

		Page<Category> categoriesPage = new PageImpl<>(category);

		when(repository.findAll(pageable)).thenReturn(categoriesPage);

		Page<CategoryDTO> result = service.searchAllCategories(pageable);

		List<String> names = result.getContent().stream().map(CategoryDTO::getName).collect(Collectors.toList());

		assertTrue(names.contains("Category 1"));
		assertTrue(names.contains("Category 2"));
	}

	@Test
	public void searchAllCategoriesShouldReturnEmptyWhenwithoutCategories() {

		Pageable pageable = PageRequest.of(0, 10);

		List<Category> categories = new ArrayList<>();

		Page<Category> categoriesPage = new PageImpl<>(categories);

		when(repository.findAll(pageable)).thenReturn(categoriesPage);

		Page<CategoryDTO> result = service.searchAllCategories(pageable);

		assertTrue(result.isEmpty());
	}

	@Test
	public void insertCategoryShouldCreateNewCategory() {

		minCategoryDTO.setName("New Category");

		Category categorySaved = Factory.constructorCategoryWithArgsAndExpense();

		when(repository.save(any(Category.class))).thenReturn(categorySaved);

		CategoryDTO result = service.insertCategory(minCategoryDTO);

		assertNotNull(result);
		assertEquals(1L, result.getId());
	}

	@Test
	public void criarCategoryShouldThrowInvalidImputExceptionWhenInvalidImput() {
		minCategoryDTO.setName("");
		

		assertThrows(InvalidInputException.class, () -> service.insertCategory(minCategoryDTO));
	}

	@Test
	public void insertCategoryShouldNotCreateWhenNameConflict() {
		
		minCategoryDTO.setName("Category Existing");

		Category categoryExisting = Factory.emptyConstructorCategory();
		categoryExisting.setId(categoryId);
		categoryExisting.setName("Category Existing");

		when(repository.findByName("Category Existing")).thenReturn(Optional.of(categoryExisting));

		CategoryDTO result = service.insertCategory(minCategoryDTO);
		assertNull(result);
	}

	@Test
	public void insertCategoryShouldSavaInReposity() {
		
		minCategoryDTO.setName("New Category");

		Category categoryToSave = Factory.emptyConstructorCategory();
		categoryToSave.setName("New Category");

		Category categorySaved = Factory.emptyConstructorCategory();
		categorySaved.setId(categoryId);
		categorySaved.setName("New Category");

		when(repository.save(any(Category.class))).thenReturn(categorySaved);

		CategoryDTO result = service.insertCategory(minCategoryDTO);

		verify(repository, times(1)).save(categoryToSave);

		assertNotNull(result);
		assertEquals("New Category", result.getName());
		assertEquals(categoryId, result.getId());
	}

	@Test
	public void insertCategoryshouldReturnDTO() {
		
		minCategoryDTO.setName("New Category");

		Category categorySaved = new Category();
		categorySaved.setId(categoryId);
		categorySaved.setName("New Category");

		when(repository.save(any(Category.class))).thenReturn(categorySaved);

		CategoryDTO result = service.insertCategory(minCategoryDTO);

		assertNotNull(result);
		assertEquals("New Category", result.getName());
		assertEquals(categoryId, result.getId());
	}

	@Test
	public void updateNameCategoryShouldFindCategoryWhenExistsId() {

		minCategoryDTO.setName("New Name");

		Category categoryExisting = Factory.emptyConstructorCategory();
		categoryExisting.setId(categoryId);
		categoryExisting.setName("Old Name");

		when(repository.findById(categoryId)).thenReturn(Optional.of(categoryExisting));
		when(repository.save(any(Category.class))).thenReturn(categoryExisting);

		service.updateNameCategory(categoryId, minCategoryDTO);

		assertEquals("New Name", categoryExisting.getName());

	}

	@Test
	public void updateNameCategoryshouldReturnDTO() {
		
		minCategoryDTO.setName("New Name");

		Category categoryExisting = Factory.emptyConstructorCategory();
		categoryExisting.setId(categoryId);
		categoryExisting.setName("Old Name");

		when(repository.findById(categoryId)).thenReturn(Optional.of(categoryExisting));
		when(repository.save(any(Category.class))).thenReturn(categoryExisting);

		CategoryDTO result = service.updateNameCategory(categoryId, minCategoryDTO);

		assertNotNull(result);
		assertEquals("New Name", result.getName());
		assertEquals(categoryId, result.getId());
	}
	
	@Test
    public void updateNameCategoryShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		
		minCategoryDTO.setName("New Name");

        when(repository.findById(nonExistentCategoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateNameCategory(nonExistentCategoryId, minCategoryDTO));
    }
	
	@Test
	public void updateNameCategoryShouldKeepSameNameWhenNameIsEqual() {
		
		minCategoryDTO.setName("Old Name"); 

	    Category categoryExisting = new Category();
	    categoryExisting.setId(categoryId);
	    categoryExisting.setName("Old Name");

	    when(repository.findById(categoryId)).thenReturn(Optional.of(categoryExisting));
	    when(repository.save(any(Category.class))).thenReturn(categoryExisting);

	    CategoryDTO result = service.updateNameCategory(categoryId, minCategoryDTO);

	    assertEquals("Old Name", result.getName());
	}
	
	@Test
	public void updateNameCategoryShouldThrowInvalidInputExceptionWhenInvalidInputValue() {
		
		minCategoryDTO.setName("");

	    assertThrows(InvalidInputException.class, () -> service.updateNameCategory(categoryId, minCategoryDTO));
	}
	
	@Test
    public void deleteCategoryShouldDeleteCategoryWhenCategoryExists() {

        doNothing().when(repository).deleteById(categoryId);

        assertDoesNotThrow(() -> service.deleteCategory(categoryId));
        
    }
	
	@Test
    public void deleteCategoryShouldThrowResourceNotFoundExceptionWhenCategoryNoExists() {

        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistentCategoryId);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteCategory(nonExistentCategoryId));
    }
	
	@Test
    public void deleteCategoryShouldThrowDatabaseExceptionWhenDataIntegrityFailure() {
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(categoryId);

        assertThrows(DatabaseException.class, () -> service.deleteCategory(categoryId));
    }

}
