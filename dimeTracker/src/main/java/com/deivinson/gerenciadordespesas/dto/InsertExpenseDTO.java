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
	private BigDecimal valor;
	private LocalDate data;
	private Long categoriaId;
	private Long usuarioId;
	
	public InsertExpenseDTO(Expense entity) {
		this.id = entity.getId();
		this.valor = entity.getValor();
		this.data = entity.getData();
		this.usuarioId = entity.getUsuario().getId();
		this.categoriaId = entity.getCategoria().getId();
	}

}
