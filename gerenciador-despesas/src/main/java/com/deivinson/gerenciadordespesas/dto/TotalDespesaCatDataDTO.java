package com.deivinson.gerenciadordespesas.dto;

import java.time.LocalDate;

public class TotalDespesaCatDataDTO {

	private LocalDate dataInicio;
    private LocalDate dataFim;
    private String categoria;
    private Double valorTotal;
    
	public TotalDespesaCatDataDTO() {
	}

	public TotalDespesaCatDataDTO(LocalDate dataInicio, LocalDate dataFim, String categoria, Double valorTotal) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.categoria = categoria;
		this.valorTotal = valorTotal;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
    
}
