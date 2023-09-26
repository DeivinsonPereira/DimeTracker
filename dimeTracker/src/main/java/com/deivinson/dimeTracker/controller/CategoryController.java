package com.deivinson.gerenciadordespesas.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deivinson.gerenciadordespesas.dto.CategoryDTO;
import com.deivinson.gerenciadordespesas.dto.MinCategoryDTO;
import com.deivinson.gerenciadordespesas.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> searchAllCategories(Pageable pageable){
		Page<CategoryDTO> dto = service.searchAllCategories(pageable);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
    public ResponseEntity<CategoryDTO> insertCategory(@RequestBody MinCategoryDTO dto) {
        CategoryDTO categoryDTO = service.insertCategory(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoryDTO);
    }
	
	@PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateNameCategory(
            @PathVariable Long categoryId,
            @RequestBody MinCategoryDTO dto) {
        CategoryDTO categoryDTO = service.updateNameCategory(categoryId, dto);
        return ResponseEntity.ok(categoryDTO);
    }
	
	@DeleteMapping(value = "/{categoryId}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        service.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
	
}
