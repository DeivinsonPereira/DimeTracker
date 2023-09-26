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

	private BigDecimal valor;
	private LocalDate data;
	private Long categoriaId;
	
}
