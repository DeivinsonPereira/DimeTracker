package com.deivinson.gerenciadordespesas.dto;

import java.time.LocalDate;

import com.deivinson.gerenciadordespesas.entities.Despesa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DespesaInserirDTO {

	private Long id;
	private Double valor;
	private LocalDate data;
	private Long categoriaId;
	private Long usuarioId;
	
	public DespesaInserirDTO(Despesa entity) {
		this.id = entity.getId();
		this.valor = entity.getValor();
		this.data = entity.getData();
		this.usuarioId = entity.getUsuario().getId();
		this.categoriaId = entity.getCategoria().getId();
	}

}
