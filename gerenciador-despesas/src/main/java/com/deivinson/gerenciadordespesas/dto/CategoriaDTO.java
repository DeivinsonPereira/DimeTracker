package com.deivinson.gerenciadordespesas.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;

public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	
	private List<DespesaDTO> despesas = new ArrayList<>();

	public CategoriaDTO() {
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<DespesaDTO> getDespesas() {
		return despesas;
	}
}
