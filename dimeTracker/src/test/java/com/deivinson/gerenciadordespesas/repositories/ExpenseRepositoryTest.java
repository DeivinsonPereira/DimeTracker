package com.deivinson.gerenciadordespesas.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.tests.Factory;

@DataJpaTest
@AutoConfigureTestDatabase
public class ExpenseRepositoryTest {
	
	@Autowired
	private ExpenseRepository expenseRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalExpenses;
	private BigDecimal valueExpense;
	
	@BeforeEach
	void setUp() {
		
		existingId = 1L;
		nonExistingId = 999L;
		countTotalExpenses = 13L;
		valueExpense = new BigDecimal("100.00");
	}
	
	@Test
	public void testSaveExpense() {
		
		Expense expense = Factory.emptyconstructorExpense();
		expense.setId(existingId);
		expense.setValueExpense(valueExpense);
		
		expenseRepository.save(expense);
		
		Expense expenseSaved = expenseRepository.findById(expense.getId()).orElse(null);
		
		assertNotNull(expenseSaved);
		assertEquals(existingId, expenseSaved.getId());
		assertEquals(expense, expenseSaved);
		assertEquals(valueExpense, expenseSaved.getValueExpense());
	}
	
	@Test
	public void testFindExpenseById() {
		
		Expense expense = Factory.emptyconstructorExpense();
		expense.setValueExpense(valueExpense);
		expenseRepository.save(expense);
		
		Long expenseId = expense.getId();
		Expense expenseFound = expenseRepository.findById(expenseId).orElse(null);
		
		assertNotNull(expenseFound);
		assertEquals(expenseId, expenseFound.getId());
		assertEquals(valueExpense, expenseFound.getValueExpense());
	}

	@Test
	public void testFindExpenseByIdNotFound() {
		
		Expense expenseFound = expenseRepository.findById(nonExistingId).orElse(null);
		
		assertNull(expenseFound);
	}
	
	@Test
	public void testFindAllExpense() {
		
		Expense expense1 = Factory.emptyconstructorExpense();
        expense1.setValueExpense(valueExpense);

        valueExpense = new BigDecimal("200.00");
        Expense expense2 = Factory.emptyconstructorExpense();
        expense2.setValueExpense(valueExpense);
        
        valueExpense = new BigDecimal("300.00");
        Expense expense3 = Factory.emptyconstructorExpense();
        expense3.setValueExpense(valueExpense);
        
        expenseRepository.save(expense1);
        expenseRepository.save(expense2);
        expenseRepository.save(expense3);
        
        List<Expense> allExpenses = expenseRepository.findAll();
        
        assertFalse(allExpenses.isEmpty());
        assertEquals(countTotalExpenses + 3, allExpenses.size());
        
        assertTrue(allExpenses.contains(expense1));
        assertTrue(allExpenses.contains(expense2));
        assertTrue(allExpenses.contains(expense3));
        
        Optional<Expense> foundExpense1 = allExpenses.stream().filter(e -> e.getValueExpense().equals(new BigDecimal("100.00"))).findFirst();
        assertTrue(foundExpense1.isPresent());

        Optional<Expense> foundExpense2 = allExpenses.stream().filter(e -> e.getValueExpense().equals(new BigDecimal("200.00"))).findFirst();
        assertTrue(foundExpense2.isPresent());

        Optional<Expense> foundExpense3 = allExpenses.stream().filter(e -> e.getValueExpense().equals(new BigDecimal("300.00"))).findFirst();
        assertTrue(foundExpense3.isPresent());
        
        
	}
	
	@Test
	public void testUpdateExpense(){
		
		Expense expense = expenseRepository.findById(1L).orElse(null);
		
		expense.setId(1L);
		expense.setValueExpense(valueExpense);
		
		expenseRepository.save(expense);
		
		assertEquals(1L, expense.getId());
		assertEquals(valueExpense, expense.getValueExpense());
		
		expense.setId(35L);
		
		BigDecimal valueExpense1 = new BigDecimal("300.00");
		expense.setValueExpense(valueExpense);
		
		expenseRepository.save(expense);
		assertNotEquals(1L, expense.getId());
		assertFalse(expense.getValueExpense() == valueExpense1);
		assertEquals(35L, expense.getId());
		assertTrue(expense.getValueExpense() == valueExpense);
		
	}
	
	@Test
	public void deleteDespesa() {
		
		Expense expense = Factory.constructorExpenseWithArgs();
		
		assertEquals(1L, expense.getId());
		
		expenseRepository.deleteById(1L);
		
		Optional<Expense> result = expenseRepository.findById(existingId);
		
		assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

		Expense expense = Factory.emptyconstructorExpense();
		expense.setId(null);
		
		expense = expenseRepository.save(expense);
		
		assertNotNull(expense);
		assertEquals(countTotalExpenses + 1L, expense.getId());
	}
	
	@Test
	public void OneToManyRelationshipExpensiveForCategory () {
		
		Expense expense = Factory.constructorExpenseWithArgs();
		
		expenseRepository.save(expense);
		
		Expense expenseRelationship = expenseRepository.findById(expense.getCategory().getId()).orElse(null); 
		assertNotNull(expenseRelationship);
		assertEquals(1L, expenseRelationship.getCategory().getId());
		assertEquals(1L, expenseRelationship.getUser().getId());
	}
	
	@Test
    public void testCalculateTotalExpensive() {
		
        BigDecimal total = expenseRepository.calculateTotalExpense();
        
        BigDecimal expectedValue = new BigDecimal("5689.36");
        
        assertEquals(expectedValue, total);
	}
	
	@Test
    public void testCalculateTotalExpensiveByCategory() {
		
        Category category = Factory.constructorCategoryWithArgsAndExpense();
        
        BigDecimal total = expenseRepository.calculateTotalExpenseByCategory(category);
        
        BigDecimal expectedValue = new BigDecimal("439.36");
        
        assertEquals(expectedValue, total);
    }
	
	@Test
    public void testCalculateTotalExpensiveByCategoryAndDate() {
		
        Category category = Factory.constructorCategoryWithArgsAndExpense();
        
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate finishDate = LocalDate.of(2023, 8, 31);
        
        BigDecimal total = expenseRepository.calculateTotalExpensesByCategoryAndDate(category, startDate, finishDate);

        BigDecimal expectedValue = new BigDecimal("439.36");
        
        assertEquals(expectedValue, total);
    }
	
	@Test
	public void testCalculateTotalDespesaByPeriod() {
	    LocalDate startDate = LocalDate.of(2023, 7, 1);
	    LocalDate finishDate = LocalDate.of(2023, 8, 31);
	    BigDecimal total = expenseRepository.calculateTotalExpensesByPeriod(startDate, finishDate);

	    BigDecimal expectedValue = new BigDecimal("3939.36");


	    assertEquals(expectedValue, total);
	}
	
}
