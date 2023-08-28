package com.deivinson.gerenciadordespesas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.services.DespesaService;

@RestController
@RequestMapping(value = "/despesas")
public class DespesaController {

	@Autowired
	private DespesaService service;
	
	@GetMapping
	public ResponseEntity<Page<DespesaDTO>> buscarTodasDespesas(Pageable pageable){
		Page<DespesaDTO> dto = service.buscarTodasDespesas(pageable);
		return ResponseEntity.ok().body(dto);
	}
	
}
