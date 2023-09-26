package com.deivinson.dimeTracker.services;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deivinson.dimeTracker.dto.ExpenseDTO;
import com.deivinson.dimeTracker.dto.InsertExpenseDTO;
import com.deivinson.dimeTracker.dto.UpdateExpenseDTO;
import com.deivinson.dimeTracker.entities.Category;
import com.deivinson.dimeTracker.entities.Expense;
import com.deivinson.dimeTracker.entities.User;
import com.deivinson.dimeTracker.repositories.CategoryRepository;
import com.deivinson.dimeTracker.repositories.ExpenseRepository;
import com.deivinson.dimeTracker.repositories.UserRepository;
import com.deivinson.dimeTracker.services.exceptions.InvalidDateException;
import com.deivinson.dimeTracker.services.exceptions.ResourceNotFoundException;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional(readOnly = true)
	public Page<ExpenseDTO> searchExpensesByFilters(Long categoryId, LocalDate startDate, LocalDate finishDate, Pageable pageable) {
        Page<Expense> expenses = null;

        if (categoryId != null && startDate != null && finishDate != null) {
            if (startDate.isAfter(finishDate)) {
                throw new InvalidDateException("Final date must be after the start date.");
            }
            expenses = repository.findByCategoryIdAndDateBetween(categoryId, startDate, finishDate, pageable);
        } else if (categoryId != null) {
            expenses = repository.findByCategoryId(categoryId, pageable);
        } else if (startDate != null && finishDate != null) {
            if (startDate.isAfter(finishDate)) {
                throw new InvalidDateException("End date cannot be before the start date.");
            }
            expenses = repository.findByDateBetween(startDate, finishDate, pageable);
        } else {
            expenses = repository.findAllWithCategory(pageable);
        }

        return expenses.map(x -> new ExpenseDTO(x));
    }
	
	@Transactional(readOnly = true)
	public BigDecimal calculateTotalExpensesByFilter(Long categoryId, LocalDate startDate, LocalDate finishDate) {
        if (categoryId != null && startDate != null && finishDate != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found!"));

            return repository.calculateTotalExpensesByCategoryAndDate(category, startDate, finishDate);
        } else if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found!"));

            return repository.calculateTotalExpenseByCategory(category);
        } else if (startDate != null && finishDate != null) {
            return repository.calculateTotalExpensesByPeriod(startDate, finishDate);
        } else {
            return repository.calculateTotalExpense();
        }
    }
	
	@Transactional
	public InsertExpenseDTO insertExpense(InsertExpenseDTO dto) {
		Expense entity = new Expense();
		copyEntity(dto, entity);
		entity = repository.save(entity);
		return new InsertExpenseDTO(entity);
	}
	
	@Transactional
    public ExpenseDTO updateExpense(Long expenseId, UpdateExpenseDTO dto) {
        try {
        	Expense expense = repository.getReferenceById(expenseId);
        	
        	if (dto.getValueExpense() != null) {
        		expense.setValueExpense(dto.getValueExpense());
        	}
        	if (dto.getDate() != null) {
        		expense.setDate(dto.getDate());
        	}
        	if (dto.getCategoryId() != null) {
        		Category category = categoryRepository.findById(dto.getCategoryId())
        				.orElseThrow(() -> new EntityNotFoundException("Category not found!"));
        		expense.setCategory(category);
        	}
        	
        	expense = repository.save(expense);
        	return new ExpenseDTO(expense);
        	
        }
	
        catch (EntityNotFoundException e) {
        	throw new ResourceNotFoundException("Id not found " + expenseId);
        }
    }
	
	@Transactional
    public void deleteExpense(Long expenseId) {
        Expense expense = repository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found!"));

        repository.delete(expense);
    }
	
	private void copyEntity(InsertExpenseDTO dto, Expense entity) {
		entity.setDate(dto.getDate());
		entity.setValueExpense(dto.getValueExpense());
		Category category = categoryRepository.findById(dto.getCategoryId())
	            .orElseThrow(() -> new EntityNotFoundException("Category not found!"));
	    entity.setCategory(category);
	    User user = userRepository.findById(dto.getUserId())
	            .orElseThrow(() -> new EntityNotFoundException("User not found!"));
	    entity.setUser(user);
	    
	}
}
