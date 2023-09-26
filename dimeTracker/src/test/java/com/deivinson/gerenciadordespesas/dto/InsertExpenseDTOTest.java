package com.deivinson.gerenciadordespesas.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.entities.User;

public class InsertExpenseDTOTest {

	private InsertExpenseDTO insertExpenseDTO;
	private BigDecimal valueExpense;
	
	@BeforeEach
	public void SetUp() {
		
		insertExpenseDTO = new InsertExpenseDTO();
		valueExpense = new BigDecimal("100.00");
	}
	
	@Test
	public void testGetAndSetId() {
		insertExpenseDTO.setId(1L);
		
		assertEquals(1L, insertExpenseDTO.getId());
	}
	
	@Test
	public void testGetAndSetValue() {
		
		insertExpenseDTO.setValueExpense(valueExpense);
		
		assertEquals(valueExpense, insertExpenseDTO.getValueExpense());
	}
	
	@Test
	public void testGetAndSetDate() {
		
		LocalDate date = LocalDate.of(2023, 1, 1);
		
		insertExpenseDTO.setDate(date);
		
		assertEquals(date, insertExpenseDTO.getDate());
		
	}
	
	@Test
	public void testGetAndSetCategoryId() {
		
		insertExpenseDTO.setCategoryId(1L);
		
		assertEquals(1L, insertExpenseDTO.getCategoryId());	
	}
	
	@Test
	public void testGetAndSetUserId() {
		insertExpenseDTO.setUserId(1L);
		
		assertEquals(1L, insertExpenseDTO.getUserId());
	}
	
	@Test
	public void testConstructorEntityToDTOTransformation() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		insertExpenseDTO = new InsertExpenseDTO(1L, valueExpense, data, 1L, 1L);
		
		assertEquals(1L, insertExpenseDTO.getId());
		assertEquals(valueExpense, insertExpenseDTO.getValueExpense());
		assertEquals(data, insertExpenseDTO.getDate());
		assertEquals(1L, insertExpenseDTO.getCategoryId());
		assertEquals(1L, insertExpenseDTO.getUserId());
	}
	
	@Test
    public void testConstrutorWihArgs() {
        Category category = new Category(1L, "Test Category");

        User user = new User(1L, "Test User");

        Expense expense = new Expense(1L, valueExpense, LocalDate.now(), user, category);

        InsertExpenseDTO insertExpenseDTO = new InsertExpenseDTO(expense);

        assertThat(insertExpenseDTO.getId()).isEqualTo(expense.getId());
        assertThat(insertExpenseDTO.getValueExpense()).isEqualTo(expense.getValueExpense());
        assertThat(insertExpenseDTO.getDate()).isEqualTo(expense.getDate());
        assertThat(insertExpenseDTO.getCategoryId()).isEqualTo(category.getId());
        assertThat(insertExpenseDTO.getUserId()).isEqualTo(user.getId());
    }
}
