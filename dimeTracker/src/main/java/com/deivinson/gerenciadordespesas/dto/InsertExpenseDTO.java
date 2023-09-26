package com.deivinson.gerenciadordespesas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.deivinson.gerenciadordespesas.entities.Expense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsertExpenseDTO {

	private Long id;
	private BigDecimal value;
	private LocalDate date;
	private Long categoryId;
	private Long userId;
	
	public InsertExpenseDTO(Expense entity) {
		this.id = entity.getId();
		this.value = entity.getValue();
		this.date = entity.getDate();
		this.userId = entity.getUser().getId();
		this.categoryId = entity.getCategory().getId();
	}

}
