package com.deivinson.gerenciadordespesas.dto;

import java.util.ArrayList;
import java.util.List;

import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;

public class UsuarioDTO {

	private Long id;
	private String nome;
	private String email;
	
	private List<DespesaDTO> despesas = new ArrayList<>();

	public UsuarioDTO() {
	}

	public UsuarioDTO(Long id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}
	
	public UsuarioDTO(Usuario entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.email = entity.getEmail();
	}
	
	public UsuarioDTO(Usuario entity, List<Despesa> despesas) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<DespesaDTO> getDespesas() {
		return despesas;
	}

}
