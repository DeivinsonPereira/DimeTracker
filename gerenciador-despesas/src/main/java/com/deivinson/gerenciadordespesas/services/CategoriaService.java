package com.deivinson.gerenciadordespesas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<CategoriaDTO> buscarTodasCategorias(Pageable pageable){
		Page<Categoria> dto = repository.findAll(pageable);
		return dto.map(x -> new CategoriaDTO(x));
	}
}
