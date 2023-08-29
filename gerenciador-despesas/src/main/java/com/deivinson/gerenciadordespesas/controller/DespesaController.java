package com.deivinson.gerenciadordespesas.controller;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deivinson.gerenciadordespesas.dto.AtualizaDespesaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaInserirDTO;
import com.deivinson.gerenciadordespesas.dto.TotalDespesaDTO;
import com.deivinson.gerenciadordespesas.services.DespesaService;

@RestController
@RequestMapping(value = "/despesas")
public class DespesaController {

	@Autowired
	private DespesaService service;
	
	@GetMapping
    public ResponseEntity<Page<DespesaDTO>> buscarDespesasPorFiltros(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            Pageable pageable) {

        Page<DespesaDTO> despesas = service.buscarDespesasPorFiltros(categoriaId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(despesas);
    }
	
	@GetMapping("/total-despesas")
	public ResponseEntity<TotalDespesaDTO> calculaTotalDespesasPorFiltros(
	        @RequestParam(required = false) Long categoriaId,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

	    Double totalDespesas = service.calcularTotalDespesasComFiltros(categoriaId, dataInicio, dataFim);

	    TotalDespesaDTO response = new TotalDespesaDTO(totalDespesas);
	    return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<DespesaInserirDTO> insert(@RequestBody DespesaInserirDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping("/{despesaId}")
    public ResponseEntity<DespesaDTO> atualizarDespesa(@PathVariable Long despesaId, @RequestBody AtualizaDespesaDTO dto) {
        DespesaDTO despesaDTO = service.atualizarDespesa(despesaId, dto);
        return ResponseEntity.ok(despesaDTO);
    }
	
	@DeleteMapping("/{despesaId}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long despesaId) {
        service.deletarDespesa(despesaId);
        return ResponseEntity.noContent().build();
    }
}
