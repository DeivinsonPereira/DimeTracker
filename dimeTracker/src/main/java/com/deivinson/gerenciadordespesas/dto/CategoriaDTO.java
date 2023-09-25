package com.deivinson.gerenciadordespesas.dto;

import com.deivinson.gerenciadordespesas.entities.Categoria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoriaDTO {

	private Long id;
	private String nome;
	
	public CategoriaDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public CategoriaDTO(Categoria entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
	}
	
}
