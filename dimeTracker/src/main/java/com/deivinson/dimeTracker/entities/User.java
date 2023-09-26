package com.deivinson.dimeTracker.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToMany(mappedBy = "user")
	private List<Expense> expenses = new ArrayList<>();
	
	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@SuppressWarnings("unused")
	private void setDespesas(List<Expense> despesa) {
	}

}