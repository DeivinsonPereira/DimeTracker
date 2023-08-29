package com.deivinson.gerenciadordespesas.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaInserirDTO;
import com.deivinson.gerenciadordespesas.dto.TotalDespesaCatDataDTO;
import com.deivinson.gerenciadordespesas.dto.TotalDespesaDTO;
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
	
	@PostMapping
	public ResponseEntity<DespesaInserirDTO> insert(@RequestBody DespesaInserirDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping("/total-despesas")
	public ResponseEntity<TotalDespesaDTO> calculaTotalDespesas(){
		Double totalDespesas = service.calcularTotalDespesas();
		
		TotalDespesaDTO response = new TotalDespesaDTO(totalDespesas);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/total-despesas/{categoriaId}")
	public ResponseEntity<TotalDespesaDTO> calculaTotalDespesasPorCategoria(@PathVariable Long categoriaId){
		Double totalDespesas = service.calcularTotalDespesasPorCategoria(categoriaId);
		
		TotalDespesaDTO response = new TotalDespesaDTO(totalDespesas);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/valor-total-categoria-e-data")
	public ResponseEntity<TotalDespesaCatDataDTO> obterValorTotalDespesasPorCategoriaEData(
	        @RequestParam Long categoriaId,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

	    TotalDespesaCatDataDTO valorTotal = service.calcularValorTotalDespesasPorCategoriaEData(categoriaId, dataInicio, dataFim);
	    
	    return ResponseEntity.ok(valorTotal);
	}
	
	@GetMapping("/categoria-e-data")
	public ResponseEntity<List<DespesaDTO>> buscarDespesasPorCategoriaEData(
			@RequestParam Long categoriaId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim){
		
		List<DespesaDTO> despesas = service.buscarDespesasPorCategoriaEData(categoriaId, dataInicio, dataFim);
		return ResponseEntity.ok(despesas);
	}
	
}
