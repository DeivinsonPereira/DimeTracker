package com.deivinson.gerenciadordespesas.dto;

import java.time.LocalDate;

import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;

public class DespesaInserirDTO {

	
	private Long id;
	private Double valor;
	private LocalDate data;
	private Long categoriaId;
	
	private Usuario usuario;

	public DespesaInserirDTO() {
	}

	public DespesaInserirDTO(Long id, Double valor, LocalDate data, Long categoriaId, Usuario usuario) {
		this.id = id;
		this.valor = valor;
		this.data = data;
		this.categoriaId = categoriaId;
		this.usuario = usuario;
	}
	
	public DespesaInserirDTO(Despesa entity) {
		this.id = entity.getId();
		this.valor = entity.getValor();
		this.data = entity.getData();
		this.categoriaId = entity.getCategoria().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
