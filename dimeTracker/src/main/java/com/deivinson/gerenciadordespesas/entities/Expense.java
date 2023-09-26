package com.deivinson.gerenciadordespesas.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name ="tb_expense")
public class Expense implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal value;
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Category category;
}
