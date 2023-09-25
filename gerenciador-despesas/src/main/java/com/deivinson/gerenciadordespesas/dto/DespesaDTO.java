package com.deivinson.gerenciadordespesas.dto;

import java.time.LocalDate;

import com.deivinson.gerenciadordespesas.entities.Despesa;

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
    private String nomeUsuario; 
    private String nomeCategoria; 
	
    public DespesaDTO(Despesa entity) {
            this.id = entity.getId();
            this.valor = entity.getValor();
            this.data = entity.getData();
            this.nomeUsuario = entity.getUsuario() != null ? entity.getUsuario().getNome() : null;
            this.nomeCategoria = entity.getCategoria() != null ? entity.getCategoria().getNome() : null;
    }

}
