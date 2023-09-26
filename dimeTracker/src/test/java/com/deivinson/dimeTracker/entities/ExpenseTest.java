package com.deivinson.dimeTracker.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.dimeTracker.tests.Factory;

public class ExpenseTest {

	private Expense expense;
	
	private BigDecimal valueExpense;
	
	@BeforeEach
	public void setUp() {
		valueExpense = new BigDecimal("100.00");
		expense = Factory.emptyconstructorExpense();;
	}
	
	@Test
	public void testGetAndSetValue() {
		expense.setId(1L);
		
		assertEquals(1L, expense.getId());
	}
	
	@Test
	public void testGetAndSetDate() {
		
		LocalDate date = LocalDate.of(2023,01,01);
		
		expense.setDate(date);
		
		assertEquals(date, expense.getDate());
		
	}
	
	@Test
	public void testGetAndSetUser() {
		expense.setUser(new User(1L, "Joe"));
		
		assertNotNull(expense.getUser());
		assertEquals(1L, expense.getUser().getId());
	}
	
	@Test
	public void testGetAndSetCategory() {
		expense.setCategory(new Category(1L, "Energy"));
		
		assertNotNull(expense.getCategory());
		assertEquals(1L, expense.getCategory().getId());
	}
	
	
	@Test
    public void testEquals() {
        Expense expense1 = new Expense(1L, valueExpense, LocalDate.now(), null, null);
        Expense expense2 = new Expense(1L, valueExpense, LocalDate.now(), null, null);

        assertThat(expense1).isEqualTo(expense2);
    }
	
	@Test
    public void testNotEquals() {
        Expense expense1 = new Expense(1L, valueExpense, LocalDate.now(), null, null);
        Expense expense2 = new Expense(2L, valueExpense, LocalDate.now(), null, null);

        assertThat(expense1).isNotEqualTo(expense2);
    }
	
	@Test
    public void testHashCode() {
        Expense expense1 = new Expense(1L, valueExpense, LocalDate.now(), null, null);
        Expense expense2 = new Expense(1L, valueExpense, LocalDate.now(), null, null);

        assertThat(expense1.hashCode()).isEqualTo(expense2.hashCode());
    }
	
	 @Test
	    public void testEqualityWithEqualInstances() {
	        Expense expense1 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);
	        Expense expense2 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(expense1).isEqualTo(expense2);
	    }
	 
	 @Test
	    public void testEqualsWithObjectsFromDifferentClasses() {
	        Expense expense1 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);
	        Expense expense2 = new Expense(2L, valueExpense, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(expense1).isNotEqualTo(expense2);
	    }
	 
	 @Test
	    public void testEqualityIfNull() {
	        Expense expense = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(expense.equals(null)).isFalse();
	    }

	    @Test
	    public void testReflexivity() {
	        Expense expense = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(expense.equals(expense)).isTrue();
	    }

	    @Test
	    public void testSymmetry() {
	        Expense expense1 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);
	        Expense expense2 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(expense1.equals(expense2)).isEqualTo(expense2.equals(expense1));
	    }

	    @Test
	    public void testTransitivity() {
	        Expense expense1 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);
	        Expense expense2 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);
	        Expense expense3 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(expense1.equals(expense2)).isTrue();
	        assertThat(expense2.equals(expense3)).isTrue();
	        assertThat(expense1.equals(expense3)).isTrue();
	    }

	    @SuppressWarnings("unlikely-arg-type")
		@Test
	    public void testEqualityWithObjectsFromDifferentClasses() {
	        Expense expense = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);
	        Category category = new Category();

	        assertThat(expense.equals(category)).isFalse();
	    }
	    
	    @Test
	    public void testConsistencyAfterChanges() {
	        Expense expense1 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);
	        Expense expense2 = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);

	        assertThat(expense1.equals(expense2)).isTrue();

	        valueExpense = new BigDecimal("150.00");
	        expense2.setValueExpense(valueExpense);

	        assertThat(expense1.getValueExpense().equals(expense2.getValueExpense())).isFalse();
	    }
	    
	    @Test
	    public void testToString() {
	        Expense expense = new Expense(1L, valueExpense, LocalDate.of(2023, 9, 25),null,null);

	        String toStringResult = expense.toString();

	        String expectedToString = "Expense(id=1, valueExpense=100.00, date=2023-09-25, user=null, category=null)";

	        assertThat(toStringResult).isEqualTo(expectedToString);
	    }
	    
}
