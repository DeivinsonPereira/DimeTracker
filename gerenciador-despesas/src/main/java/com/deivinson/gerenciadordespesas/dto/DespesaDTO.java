package com.deivinson.gerenciadordespesas.dto;

import java.time.Instant;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;

public class DespesaDTO {

	private Long id;
	private Double valor;
	private Instant data;
	
	private Usuario usuario;
	
	private Categoria categoria;
	
	public DespesaDTO() {
	}

	public DespesaDTO(Long id, Double valor, Instant data, Usuario usuario, Categoria categoria) {
		this.id = id;
		this.valor = valor;
		this.data = data;
		this.usuario = usuario;
		this.categoria = categoria;
	}
	
	public DespesaDTO(Despesa entity) {
		this.id = entity.getId();
		this.valor = entity.getValor();
		this.data = entity.getData();
		this.usuario = entity.getUsuario();
		this.categoria = entity.getCategoria();
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

	public Instant getData() {
		return data;
	}

	public void setData(Instant data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
