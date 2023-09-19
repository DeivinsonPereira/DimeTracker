package com.deivinson.gerenciadordespesas.dto;

import java.util.ArrayList;
import java.util.List;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaDTO {

	private Long id;
	private String nome;
	
	private List<DespesaDTO> despesas = new ArrayList<>();

	public CategoriaDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public CategoriaDTO(Categoria entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
	}
	
	public CategoriaDTO(Categoria entity, List<Despesa> despesas) {
		this(entity);
		despesas.forEach(desp -> this.despesas.add(new DespesaDTO(desp)));
		
	}

	@SuppressWarnings("unused")
	private void setDespesas(List<DespesaDTO> despesaDTO) {
	}
}
