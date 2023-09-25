package com.deivinson.gerenciadordespesas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "usuario")
	private List<Despesa> despesas = new ArrayList<>();
	
	public Usuario(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	@SuppressWarnings("unused")
	private void setDespesas(List<Despesa> despesa) {
	}

}