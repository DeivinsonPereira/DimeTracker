package com.deivinson.gerenciadordespesas.dto;

import java.time.LocalDate;

public class AtualizaDespesaDTO {

	private Double valor;
	private LocalDate data;
	private Long categoriaId;
	
	public AtualizaDespesaDTO() {
	}

	public AtualizaDespesaDTO(Double valor, LocalDate data, Long categoriaId) {
		this.valor = valor;
		this.data = data;
		this.categoriaId = categoriaId;
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
	
}
