package com.deivinson.gerenciadordespesas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
@Table(name = "tb_category")
public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
	private List<Expense> expenses = new ArrayList<>();
	
	
	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@SuppressWarnings("unused")
	private void SetExpenses(List<Expense> expenses) {}

}
