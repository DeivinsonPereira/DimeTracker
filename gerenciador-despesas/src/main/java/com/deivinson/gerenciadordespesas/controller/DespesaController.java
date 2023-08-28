package com.deivinson.gerenciadordespesas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<List<DespesaDTO>> buscarTodasDespesas(){
		List<DespesaDTO> dto = service.buscarTodasDespesas();
		return ResponseEntity.ok().body(dto);
	}
	
}
