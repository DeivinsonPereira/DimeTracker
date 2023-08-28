package com.deivinson.gerenciadordespesas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deivinson.gerenciadordespesas.dto.CategoriaDTO;
import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.respositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;	
	
	@Transactional(readOnly = true)
	public List<CategoriaDTO> buscarTodasCategorias(){
		List<Categoria> lista = repository.findAll();
		return lista.stream().map(x -> new CategoriaDTO(x)).toList();
	}
	
	
}
