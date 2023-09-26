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
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalCategories;
	
	@BeforeEach
	void setUp() {
		
		existingId = 1L;
		nonExistingId = 999L;
		countTotalCategories = 4L;
	}
	
	@Test
	public void testSaveCategory() {
		
		Category category = Factory.emptyConstructorCategory();
		category.setId(existingId);
		category.setName("Energy");
		
		categoryRepository.save(category);
		
		Category categorySave = categoryRepository.findById(category.getId()).orElse(null);
		
		assertNotNull(categorySave);
		assertEquals(existingId, categorySave.getId());
		assertEquals(category, categorySave);
		assertTrue(categorySave.getName().equalsIgnoreCase("Energy"));
	}
	
	@Test
	public void testFindCategoryById() {
		
		Category category = Factory.emptyConstructorCategory();
		category.setName("Test");
		categoryRepository.save(category);
		
		Long categoryId = category.getId();
		Category categoryFound = categoryRepository.findById(categoryId).orElse(null);
		
		assertNotNull(categoryFound);
		assertEquals(categoryId, categoryFound.getId());
		assertEquals("Test", categoryFound.getName());
	}

	@Test
	public void testFindCategoryByIdNotFound() {
		
		Category categoryFound = categoryRepository.findById(nonExistingId).orElse(null);
		
		assertNull(categoryFound);
	}
	
	@Test
	public void testFindAllCategory() {
		
		Category category1 = Factory.emptyConstructorCategory();
        category1.setName("Category 1");

        Category category2 = Factory.emptyConstructorCategory();
        category2.setName("Category 2");
        
        Category category3 = Factory.emptyConstructorCategory();
        category3.setName("Category 3");
        
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        
        List<Category> allCategories = categoryRepository.findAll();
        
        assertFalse(allCategories.isEmpty());
        assertEquals(countTotalCategories + 3, allCategories.size());
        
        assertTrue(allCategories.stream().anyMatch(c -> c.getName().equals("Category 1")));
        assertTrue(allCategories.stream().anyMatch(c -> c.getName().equals("Category 2")));
        assertTrue(allCategories.stream().anyMatch(c -> c.getName().equals("Category 3")));
        
	}
	
	@Test
	public void testUpdateCategory(){
		
		Category category = categoryRepository.findById(1L).orElse(null);
		
		category.setId(1L);
		category.setName("Joe");
		
		categoryRepository.save(category);
		
		assertEquals(1L, category.getId());
		assertTrue(category.getName().equalsIgnoreCase("Joe"));
		
		category.setId(35L);
		category.setName("Natasha");
		
		categoryRepository.save(category);
		assertNotEquals(1L, category.getId());
		assertFalse(category.getName().equalsIgnoreCase("Joe"));
		assertEquals(35L, category.getId());
		assertTrue(category.getName().equalsIgnoreCase("Natasha"));
		
	}
	
	@Test
	public void deleteCategory() {
		Category category = Factory.constructorCategoryWihArgs();
		
		assertEquals(1L, category.getId());
		assertTrue(category.getName().equalsIgnoreCase("Energy"));
		
		categoryRepository.deleteById(1L);
		
		Optional<Category> result = categoryRepository.findById(existingId);
		
		assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

		Category category = new Category();
		category.setId(null);
		
		category = categoryRepository.save(category);
		
		assertNotNull(category);
		assertEquals(countTotalCategories + 1L, category.getId());
	}
	
	@Test
	public void OneToManyRelationshipCategoryForExpense () {
		Category category1 = Factory.constructorCategoryWithArgsAndExpense();
		
		categoryRepository.save(category1);
		
		Category categoryRelationship = categoryRepository.findById(category1.getId()).orElse(null); 
		assertNotNull(categoryRelationship);
		assertEquals(1, categoryRelationship.getExpenses().size());
		
		for(Expense expense : category1.getExpenses()) {
			assertEquals(categoryRelationship, expense.getCategory());
		}
	}
	
	@Test
	public void testCascadeRemoval() {
		Category category1 = Factory.constructorCategoryWithArgsAndExpense();
		
		categoryRepository.save(category1);
		
		Category categoryCascade = categoryRepository.findById(category1.getId()).orElse(null); 
		assertNotNull(categoryCascade);
		assertEquals(1, categoryCascade.getExpenses().size());
		
		categoryRepository.delete(categoryCascade);
		
		Category categoryRemoved = categoryRepository.findById(categoryCascade.getId()).orElse(null);
		
	    assertNull(categoryRemoved);
	    
	    Optional <Expense> expense = expenseRepository.findById(category1.getId());
	    
	    assertTrue(expense.isEmpty());
	}
	
}
