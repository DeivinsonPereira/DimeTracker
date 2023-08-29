package com.deivinson.gerenciadordespesas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deivinson.gerenciadordespesas.dto.CategoriaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.services.CategoriaService;
import com.deivinson.gerenciadordespesas.services.DespesaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;
	
	@Autowired
	private DespesaService despesaService;
	
	@GetMapping
	public ResponseEntity<Page<CategoriaDTO>> buscarTodasCategorias(Pageable pageable){
		Page<CategoriaDTO> dto = service.buscarTodasCategorias(pageable);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<List<DespesaDTO>> buscarDespesasPorCategoria(@PathVariable Long id) {
		List<DespesaDTO> dto = despesaService.buscarDespesasPorCategoria(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{categoriaId}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Long categoriaId) {
        service.deletarCategoria(categoriaId);
        return ResponseEntity.noContent().build();
    }
	
}
