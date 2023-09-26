package com.deivinson.gerenciadordespesas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.deivinson.gerenciadordespesas.dto.ExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.InsertExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.UpdateExpenseDTO;
import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.entities.User;
import com.deivinson.gerenciadordespesas.repositories.CategoryRepository;
import com.deivinson.gerenciadordespesas.repositories.ExpenseRepository;
import com.deivinson.gerenciadordespesas.repositories.UserRepository;
import com.deivinson.gerenciadordespesas.services.exceptions.InvalidDateException;
import com.deivinson.gerenciadordespesas.services.exceptions.ResourceNotFoundException;
import com.deivinson.gerenciadordespesas.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ExpenseServiceTest {

	@InjectMocks
	private ExpenseService service;
	
	@Mock
	private ExpenseRepository repository;
	
    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private UserRepository usuarioRepository;
	
	private Long categoryId;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Expense expense1;
    private Expense expense2;
    private Expense expense3;
    
    BigDecimal b1;
    BigDecimal b2;
    BigDecimal b3;
    BigDecimal c1;
    
    
    private Page<Expense> page;
    
    private List<Expense> expenses;

	@BeforeEach
	void setUp() throws Exception {
		
		expense1 = Factory.emptyconstructorExpense();
		expense1.setId(1L);
		b1 = new BigDecimal("100.00");
		expense1.setValueExpense(b1);
		expense1.setDate(finishDate);

		expense2 = Factory.emptyconstructorExpense();
		expense2.setId(2L);
		b2 = new BigDecimal("200.00");
		expense2.setValueExpense(b2);
		expense2.setDate(finishDate);

		expense3 = Factory.emptyconstructorExpense();
		expense3.setId(3L);
		b3 = new BigDecimal("300.00");
		expense3.setValueExpense(b3);
		expense3.setDate(finishDate);
		
		c1 = new BigDecimal("50.0");
		
		expenses = Arrays.asList(expense1, expense2, expense3);
		
		page = new PageImpl<>(expenses);
		
		categoryId = 1L;
        startDate = LocalDate.of(2023, 1, 1);
        finishDate = LocalDate.of(2023, 12, 31);
        
        
	}
	
	@Test
	public void searchExpensesByFiltersShouldGetExpenseByCategoryIdWhenCategoryIdExists() {
		
	    when(repository.findAllWithCategory(any(Pageable.class))).thenReturn(page);

	    Page<ExpenseDTO> expensesDTO = service.searchExpensesByFilters(null, null, null, Pageable.unpaged());

	    verify(repository).findAllWithCategory(Pageable.unpaged());

	    List<ExpenseDTO> listexpensesDTO = expenses.stream()
	            .map(expense -> new ExpenseDTO(expense))
	            .collect(Collectors.toList());
	    assertEquals(new PageImpl<>(listexpensesDTO), expensesDTO);
	}
	
	@Test
    public void searchExpensesByFiltersShoulGetAllExpensesFromCategoryWhenSpecifyCategoryId() {
		 
        when(repository.findByCategoryId(eq(categoryId), any(Pageable.class))).thenReturn(page);

        Page<ExpenseDTO> expensesDTO = service.searchExpensesByFilters(categoryId, null, null, Pageable.unpaged());

        verify(repository).findByCategoryId(eq(categoryId), any(Pageable.class));
        
        List<ExpenseDTO> expensesDTOMockadas = expenses.stream()
                .map(expense -> new ExpenseDTO(expense))
                .collect(Collectors.toList());
        assertEquals(new PageImpl<>(expensesDTOMockadas), expensesDTO);
    }
	 
	@Test
	public void searchExpensesByFiltersShoulGetAllExpensesWithinDateRangeWhenDatesAreProvided() {

	    when(repository.findByDateBetween(eq(startDate), eq(finishDate), any(Pageable.class))).thenReturn(page);

	    Page<ExpenseDTO> expensesDTO = service.searchExpensesByFilters(null, startDate, finishDate, Pageable.unpaged());

	    verify(repository).findByDateBetween(eq(startDate), eq(finishDate), any(Pageable.class));

	    List<ExpenseDTO> expensesDTOMockadas = expenses.stream()
	            .map(expense -> new ExpenseDTO(expense))
	            .collect(Collectors.toList());
	    assertEquals(new PageImpl<>(expensesDTOMockadas), expensesDTO);
	}

	
	@Test
	public void searchExpensesByFiltersShouldGetAllExpensesByCategoryAndDateWhenCategoryAndDateAreProvided() {

	    when(repository.findByCategoryIdAndDateBetween(eq(categoryId), eq(startDate), eq(finishDate), any(Pageable.class)))
	            .thenReturn(page);

	    Page<ExpenseDTO> expensesDTO = service.searchExpensesByFilters(categoryId, startDate, finishDate,
	            Pageable.unpaged());

	    verify(repository).findByCategoryIdAndDateBetween(eq(categoryId), eq(startDate), eq(finishDate),
	            any(Pageable.class));

	    List<ExpenseDTO> expensesDTOMocked = expenses.stream()
	            .map(expense -> new ExpenseDTO(expense))
	            .collect(Collectors.toList());
	    assertEquals(new PageImpl<>(expensesDTOMocked), expensesDTO);
	}
	
	@Test
    public void searchExpensesByFiltersShouldGetAllExpenses() {
		
        Category category = Factory.emptyConstructorCategory();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        when(repository.calculateTotalExpensesByCategoryAndDate(category, startDate, finishDate)).thenReturn(b1);
        when(repository.calculateTotalExpenseByCategory(category)).thenReturn(c1);
        when(repository.calculateTotalExpensesByPeriod(startDate, finishDate)).thenReturn(b2);
        when(repository.calculateTotalExpense()).thenReturn(b3);

        BigDecimal result1 = service.calculateTotalExpensesByFilter(categoryId, startDate, finishDate);
        BigDecimal result2 = service.calculateTotalExpensesByFilter(categoryId, null, null);
        BigDecimal result3 = service.calculateTotalExpensesByFilter(null, startDate, finishDate);
        BigDecimal result4 = service.calculateTotalExpensesByFilter(null, null, null);

        assertEquals(b1, result1);
        assertEquals(c1, result2);
        assertEquals(b2, result3);
        assertEquals(b3, result4);
    }
	
	@Test
    public void searchExpensesByFiltersShouldGetAllExpensesWhenNoArgsAreProvided() {
        Pageable pageable = Pageable.unpaged();
        Category category = new Category(1L, "Nutrition");
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1L, b1, LocalDate.of(2023, 1, 15), null, category));
        expenses.add(new Expense(2L, b2, LocalDate.of(2023, 1, 20), null, category));
        Page<Expense> page = new PageImpl<>(expenses);

        when(repository.findAllWithCategory(pageable)).thenReturn(page);

        Page<ExpenseDTO> result = service.searchExpensesByFilters(null, null, null, pageable);

        assertEquals(2, result.getTotalElements());
    }
	
	@Test
    public void searchExpensesByFiltersShouldThrowsInvalidDateExceptionWhenInvalidDateProved() {
        LocalDate startDate = LocalDate.of(2023, 2, 1);
        LocalDate finishDate = LocalDate.of(2023, 1, 1);

        assertThrows(InvalidDateException.class, () -> service.searchExpensesByFilters(null, startDate, finishDate, Pageable.unpaged()));
    }
	
	@Test
	public void searchExpensesByFiltersShouldThrowsInvalidDataExceptionWhenFinishDateBeforeStartDate() {
	    LocalDate startDate = LocalDate.of(2023, 2, 15);
	    LocalDate finishDate = LocalDate.of(2023, 2, 10); 

	    assertThrows(InvalidDateException.class, () -> {
	        service.searchExpensesByFilters(null, startDate, finishDate, Pageable.unpaged());
	    });
	}
	
	@Test
	public void searchExpensesByFiltersShouldReturnEmptyWhenCategoryNonExists() {

	    when(repository.findByCategoryId(eq(categoryId), any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

	    Page<ExpenseDTO> expensesDTO = service.searchExpensesByFilters(categoryId, null, null, Pageable.unpaged());

	    verify(repository).findByCategoryId(eq(categoryId), any(Pageable.class));

	    assertEquals(0, expensesDTO.getTotalElements());
	}
	
	@Test
    public void searchExpensesByFiltersShouldThrowsInvalidDataExceptionWhenInvalidDateAndValidCategory() {
        categoryId = 1L;
        startDate = LocalDate.of(2023, 2, 1);
        finishDate = LocalDate.of(2023, 1, 1);


        assertThrows(InvalidDateException.class, () -> service.searchExpensesByFilters(categoryId, startDate, finishDate, Pageable.unpaged()));

        verify(repository, never()).findByCategoryIdAndDateBetween(any(), any(), any(), any());
    }
	
	@Test
    public void deleteExpenseShouldDeleteExpenseExistingWhenExpenseIdExisting() {
        Expense expense = Factory.constructorExpenseWithArgs();

        when(repository.findById(1L)).thenReturn(Optional.of(expense));

        service.deleteExpense(1L);

        verify(repository).delete(expense);
    }

	@Test
    public void deleteExpenseShouldThrowsEntityNotFoundExceptionWhenExpenseIdNotExists() {
		Expense expense = Factory.constructorExpenseWithArgs();
	 
        when(repository.findById(expense.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deleteExpense(1L));
    }
	
	@Test
    public void insertExpenseShouldInsertNewExpense() {
		InsertExpenseDTO dto = new InsertExpenseDTO();
		dto.setId(categoryId);
		dto.setValueExpense(b1);
		dto.setDate(LocalDate.of(2023, 10, 15));
		dto.setCategoryId(1L);
		dto.setUserId(1L);

	    Category category = Factory.emptyConstructorCategory();
	    category.setId(1L);
	    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

	    User user = Factory.emptyConstructorUser();
	    user.setId(1L);
	    when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(user));

	    Expense expense = Factory.emptyconstructorExpense();
	    expense.setId(1L);
	    expense.setValueExpense(b1);
	    expense.setDate(LocalDate.of(2023, 10, 15));
	    expense.setCategory(category);
	    expense.setUser(user);
	    
	    when(repository.save(any(Expense.class))).thenReturn(expense);
	    
	    InsertExpenseDTO resultDTO = service.insertExpense(dto);

	    verify(repository).save(any(Expense.class));
	    

	    assertEquals(expense.getId(), resultDTO.getId());
	    assertEquals(dto.getValueExpense(), resultDTO.getValueExpense()); 
	    assertEquals(dto.getDate(), resultDTO.getDate());
    }
	
	@Test
    public void updateExpenseShouldUpdateExpenseExists() {
		Long expenseId = 1L;
        UpdateExpenseDTO dto = new UpdateExpenseDTO(b1, LocalDate.now(), 2L);
        
        Category category = new Category();
        category.setId(2L);
        
        User user = new User();
        user.setId(2L);
        
        Expense expenseExisting = new Expense();
        expenseExisting.setId(expenseId);
        expenseExisting.setCategory(category);
        expenseExisting.setUser(user);

        when(repository.save(any(Expense.class))).thenReturn(expenseExisting);

        when(repository.getReferenceById(expenseId)).thenReturn(expenseExisting);
        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.of(category));

        ExpenseDTO resultado = service.updateExpense(expenseId, dto);

        assertNotNull(resultado);
        assertEquals(dto.getValueExpense(), resultado.getValueExpense());
        assertEquals(dto.getDate(), resultado.getDate());
        assertEquals(category.getName(), resultado.getCategoryName());
    }
	
	@Test
	public void updateExpenseShouldThrowsResourceNotFoundExceptionWhenExpenseIdNotFound() {
		Long expenseId = 1L;
	    UpdateExpenseDTO dto = new UpdateExpenseDTO(b1, LocalDate.now(), 2L);

	    when(repository.getReferenceById(expenseId)).thenReturn(new Expense()); 
	    when(repository.findById(expenseId)).thenReturn(Optional.empty());

	    assertThrows(ResourceNotFoundException.class, () -> service.updateExpense(expenseId, dto));
	}
	
	@Test
    public void testCopyEntity() throws Exception{
		InsertExpenseDTO dto = new InsertExpenseDTO();
        dto.setDate(LocalDate.now());
        dto.setValueExpense(b1);
        dto.setCategoryId(1L);
        dto.setUserId(2L);

        Expense entity = new Expense();

        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        User user = new User();
        user.setId(2L);
        user.setName("Test User");

        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.of(category));
        when(usuarioRepository.findById(dto.getUserId())).thenReturn(Optional.of(user));

        Method privateMethod = ExpenseService.class.getDeclaredMethod("copyEntity", InsertExpenseDTO.class, Expense.class);
        privateMethod.setAccessible(true);
        privateMethod.invoke(service, dto, entity);

        assertEquals(dto.getDate(), entity.getDate());
        assertEquals(dto.getValueExpense(), entity.getValueExpense()); 
        assertEquals(category, entity.getCategory());
        assertEquals(user, entity.getUser());
    }
	
	@Test
    public void copyEntityShouldThrowsEntityNotFoundExceptionWhenCategoryNotFound() throws Exception {
		
		InsertExpenseDTO dto = new InsertExpenseDTO();
        dto.setCategoryId(1L);

        Expense entity = new Expense();

        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.empty());

        Method privateMethod = ExpenseService.class.getDeclaredMethod("copyEntity", InsertExpenseDTO.class, Expense.class);
        privateMethod.setAccessible(true);

        try {
            privateMethod.invoke(service, dto, entity);
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertEquals(EntityNotFoundException.class, actualException.getClass());
            assertEquals("Category not found!", actualException.getMessage());
        }
	}

	@Test
    public void copyEntityShouldThrowsEntityNotFoundExceptionWhenUserNotFound() throws Exception {
		 
		InsertExpenseDTO dto = new InsertExpenseDTO();
        dto.setUserId(2L); 

        Expense entity = new Expense();

        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.of(new Category()));
        when(usuarioRepository.findById(dto.getUserId())).thenReturn(Optional.empty());

        Method privateMethod = ExpenseService.class.getDeclaredMethod("copyEntity", InsertExpenseDTO.class, Expense.class);
        privateMethod.setAccessible(true);

        try {
            privateMethod.invoke(service, dto, entity);
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertEquals(EntityNotFoundException.class, actualException.getClass());
            assertEquals("User not found!", actualException.getMessage());
        }
    }
}