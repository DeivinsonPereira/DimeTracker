package com.deivinson.gerenciadordespesas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deivinson.gerenciadordespesas.dto.CategoriaDTO;
import com.deivinson.gerenciadordespesas.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;
	
	@GetMapping
	public ResponseEntity<Page<CategoriaDTO>> buscarTodasCategorias(Pageable pageable){
		Page<CategoriaDTO> dto = service.buscarTodasCategorias(pageable);
		return ResponseEntity.ok().body(dto);
	}
	
}
