package com.deivinson.gerenciadordespesas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.deivinson.gerenciadordespesas.entities.Expense;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {

	private Long id;
    private BigDecimal value;
    private LocalDate date;
    private String userName; 
    private String categoryName; 
	
    public ExpenseDTO(Expense entity) {
            this.id = entity.getId();
            this.value = entity.getValor();
            this.date = entity.getData();
            this.userName = entity.getUsuario() != null ? entity.getUsuario().getNome() : null;
            this.categoryName = entity.getCategoria() != null ? entity.getCategoria().getNome() : null;
    }

}