package com.deivinson.gerenciadordespesas.dto;

import java.time.LocalDate;

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaDTO {

	private Long id;
	private Double valor;
	private LocalDate data;
	
	private Usuario usuario;
	
	private Categoria categoria;
	
	public DespesaDTO(Despesa entity) {
		this.id = entity.getId();
		this.valor = entity.getValor();
		this.data = entity.getData();
		this.usuario = entity.getUsuario();
		this.categoria = entity.getCategoria();
	}

}
