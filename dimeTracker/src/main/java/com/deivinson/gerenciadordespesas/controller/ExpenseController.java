package com.deivinson.gerenciadordespesas.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deivinson.gerenciadordespesas.dto.ExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.InsertExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.TotalExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.UpdateExpenseDTO;
import com.deivinson.gerenciadordespesas.services.ExpenseService;

@RestController
@RequestMapping(value = "/expenses")
public class ExpenseController {

	@Autowired
	private ExpenseService service;
	
	@GetMapping
	public ResponseEntity<Page<ExpenseDTO>> searchExpensesByFilters(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate,
            Pageable pageable) {

        Page<ExpenseDTO> expenses = service.searchExpensesByFilters(categoryId, startDate, finishDate, pageable);
        return ResponseEntity.ok(expenses);
    }
	
	@GetMapping("/total-expenses")
	public ResponseEntity<TotalExpenseDTO> calculateTotalExpensesByFilter(
	        @RequestParam(required = false) Long categoryId,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate) {

		BigDecimal totalExpenses = service.calculateTotalExpensesByFilter(categoryId, startDate, finishDate);

	    TotalExpenseDTO response = new TotalExpenseDTO(totalExpenses);
	    return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<InsertExpenseDTO> insertExpense(@RequestBody InsertExpenseDTO dto){
		dto = service.insertExpense(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long categoryId, @RequestBody UpdateExpenseDTO dto) {
        ExpenseDTO expenseDTO = service.updateExpense(categoryId, dto);
        return ResponseEntity.ok(expenseDTO);
    }
	
	@DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long expenseId) {
        service.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}
