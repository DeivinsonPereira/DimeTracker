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
    private BigDecimal valueExpense;
    private LocalDate date;
    private String userName; 
    private String categoryName; 
	
    public ExpenseDTO(Expense entity) {
            this.id = entity.getId();
            this.valueExpense = entity.getValueExpense();
            this.date = entity.getDate();
            this.userName = entity.getUser() != null ? entity.getUser().getName() : null;
            this.categoryName = entity.getCategory() != null ? entity.getCategory().getName() : null;
    }

}
