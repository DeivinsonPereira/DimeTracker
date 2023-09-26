package com.deivinson.gerenciadordespesas.dto;

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

	private BigDecimal value;
	private LocalDate date;
	private Long categoryId;
	
}
