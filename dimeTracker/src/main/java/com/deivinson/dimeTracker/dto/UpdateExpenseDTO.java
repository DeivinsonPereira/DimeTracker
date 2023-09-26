package com.deivinson.dimeTracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExpenseDTO {

	private BigDecimal valueExpense;
	private LocalDate date;
	private Long categoryId;
	
}
