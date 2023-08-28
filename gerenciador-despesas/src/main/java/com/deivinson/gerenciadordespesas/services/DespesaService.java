package com.deivinson.gerenciadordespesas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.respositories.DespesaRepository;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository repository;
	
	@Transactional(readOnly = true)
	public Page<DespesaDTO> buscarTodasDespesas(Pageable pageable){
		Page<Despesa> dto = repository.findAll(pageable);
		return dto.map(x -> new DespesaDTO(x));
	}
	
	
}
