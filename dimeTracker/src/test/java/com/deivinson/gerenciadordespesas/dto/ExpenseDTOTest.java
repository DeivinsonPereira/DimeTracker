package com.deivinson.gerenciadordespesas.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.entities.User;

public class ExpenseDTOTest {

	private ExpenseDTO expenseDTO;
	private BigDecimal valueExpense;
	
	@BeforeEach
	public void SetUp() {
		
		valueExpense = new BigDecimal("100.0");
		expenseDTO = new ExpenseDTO();
	}
	
	@Test
	public void testGetAndSetId() {
		expenseDTO.setId(1L);
		
		assertEquals(1L, expenseDTO.getId());
	}
	
	@Test
	public void testGetAndSetValue() {
		expenseDTO.setValueExpense(valueExpense);
		
		assertEquals(valueExpense, expenseDTO.getValueExpense());
	}
	
	@Test
	public void testGetAndSetDate() {
		
		LocalDate date = LocalDate.of(2023, 1, 1);
		
		expenseDTO.setDate(date);
		
		assertEquals(date, expenseDTO.getDate());
	}
	
	@Test
	public void testGetAndSetUser() {
		expenseDTO.setUserName("Joe");
		
		assertTrue(expenseDTO.getUserName().equalsIgnoreCase("Joe"));
	}
	
	@Test
	public void testGetAndSetCategory() {
		expenseDTO.setCategoryName("Energy");
		
		assertTrue(expenseDTO.getCategoryName().equalsIgnoreCase("Energy"));
	}
	
	@Test
	public void testConstructorEntityToDTOTransformation() {
		LocalDate data = LocalDate.of(2023, 1, 1);
		
		expenseDTO = new ExpenseDTO(1L, valueExpense, data, "Joe", "Energy");
		
		
		assertEquals(1L, expenseDTO.getId());
		assertEquals(valueExpense, expenseDTO.getValueExpense());
		assertEquals(data, expenseDTO.getDate());
		assertTrue(expenseDTO.getUserName().equalsIgnoreCase("Joe"));
		assertTrue(expenseDTO.getCategoryName().equalsIgnoreCase("Energy"));
		
	}
	
	@Test
    public void testConstructorWithArgs() {
        Category category = new Category(1L, "Test Category");

        User user = new User(1L, "Test User");

        Expense expense = new Expense(1L, valueExpense, LocalDate.now(), user, category);

        ExpenseDTO expenseDTO = new ExpenseDTO(expense);

        assertThat(expenseDTO.getId()).isEqualTo(expense.getId());
        assertThat(expenseDTO.getValueExpense()).isEqualTo(expense.getValueExpense());
        assertThat(expenseDTO.getDate()).isEqualTo(expense.getDate());
        assertThat(expenseDTO.getUserName()).isEqualTo(user.getName());
        assertThat(expenseDTO.getCategoryName()).isEqualTo(category.getName());
    }
	
	@Test
    public void testConstructorWithArgsWithUserNameandCategoryName() {
        Category category = new Category(1L, "Test Category");

        User user = new User(1L, "Test Category");

        Expense expense = new Expense(1L, valueExpense, LocalDate.now(), user, category);

        ExpenseDTO expenseDTO = new ExpenseDTO(expense);

        assertThat(expenseDTO.getUserName()).isEqualTo(user.getName());
        assertThat(expenseDTO.getCategoryName()).isEqualTo(category.getName());
    }

    @Test
    public void testConstructorWithArgumentsWithoutUserNameAndCategoryName() {
        Expense expense = new Expense(1L, valueExpense, LocalDate.now(), null, null);

        ExpenseDTO expenseDTO = new ExpenseDTO(expense);

        assertThat(expenseDTO.getUserName()).isNull();
        assertThat(expenseDTO.getCategoryName()).isNull();
    }
    
    @Test
    public void testEqualsAndHashCode() {
        ExpenseDTO expenseDTO1 = new ExpenseDTO(1L, valueExpense, LocalDate.now(), "User", "Category");
        ExpenseDTO expenseDTO2 = new ExpenseDTO(1L, valueExpense, LocalDate.now(), "User", "Category");

        assertThat(expenseDTO1).isEqualTo(expenseDTO2);

        assertThat(expenseDTO1.hashCode()).isEqualTo(expenseDTO2.hashCode());

        valueExpense = new BigDecimal("200.00");
        ExpenseDTO expenseDTO3 = new ExpenseDTO(2L, valueExpense, LocalDate.now().plusDays(1), "OtherUser", "OtherCategory");

        assertThat(expenseDTO1).isNotEqualTo(expenseDTO3);

        assertThat(expenseDTO1.hashCode()).isNotEqualTo(expenseDTO3.hashCode());

        assertThat(expenseDTO1.equals(null)).isFalse();

    }
}
