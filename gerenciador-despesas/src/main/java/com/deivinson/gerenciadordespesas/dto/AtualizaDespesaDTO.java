package com.deivinson.gerenciadordespesas.dto;

import java.time.LocalDate;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizaDespesaDTO {

	private Double valor;
	private LocalDate data;
	private Long categoriaId;
	
}
