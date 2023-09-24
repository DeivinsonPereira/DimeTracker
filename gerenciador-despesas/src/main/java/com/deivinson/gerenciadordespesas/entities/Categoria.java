package com.deivinson.gerenciadordespesas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
@Table(name = "tb_categoria")
public class Categoria implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE)
	private List<Despesa> despesas = new ArrayList<>();
	
	
	public Categoria(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@SuppressWarnings("unused")
	private void SetDespesas(List<Despesa> despesas) {}

}
