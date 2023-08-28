package com.deivinson.gerenciadordespesas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<DespesaDTO> buscarTodasDespesas(){
		List<Despesa> dto = repository.findAll();
		return dto.stream().map(x -> new DespesaDTO(x)).toList();
	}
	
	
}
