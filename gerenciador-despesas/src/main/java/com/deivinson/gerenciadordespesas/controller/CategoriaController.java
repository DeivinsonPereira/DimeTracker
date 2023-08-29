package com.deivinson.gerenciadordespesas.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deivinson.gerenciadordespesas.dto.CategoriaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.dto.MinCategoriaDTO;
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
	
	@PostMapping
    public ResponseEntity<CategoriaDTO> criarCategoria(@RequestBody MinCategoriaDTO dto) {
        CategoriaDTO categoriaDTO = service.criarCategoria(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoriaDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoriaDTO);
    }
	
	@DeleteMapping(value = "/{categoriaId}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Long categoriaId) {
        service.deletarCategoria(categoriaId);
        return ResponseEntity.noContent().build();
    }
	
}
