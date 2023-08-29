package com.deivinson.gerenciadordespesas.dto;

import java.time.LocalDate;

public class DespesaCategoriaDataInfoDTO {

	private Long id;
	private Double valor;
	private LocalDate data;
	private String categoria;
	
	public DespesaCategoriaDataInfoDTO() {
		
	}

	public DespesaCategoriaDataInfoDTO(Long id, Double valor, LocalDate data, String categoria) {
		this.id = id;
		this.valor = valor;
		this.data = data;
		this.categoria = categoria;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
