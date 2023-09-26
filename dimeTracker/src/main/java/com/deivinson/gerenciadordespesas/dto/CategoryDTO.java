package com.deivinson.gerenciadordespesas.dto;

import com.deivinson.gerenciadordespesas.entities.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDTO {

	private Long id;
	private String nome;
	
	public CategoryDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
	}
	
}