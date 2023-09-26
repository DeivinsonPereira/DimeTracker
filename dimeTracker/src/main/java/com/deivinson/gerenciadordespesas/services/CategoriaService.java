package com.deivinson.gerenciadordespesas.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deivinson.gerenciadordespesas.dto.CategoryDTO;
import com.deivinson.gerenciadordespesas.dto.MinCategoryDTO;
import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.repositories.CategoryRepository;
import com.deivinson.gerenciadordespesas.services.exceptions.DatabaseException;
import com.deivinson.gerenciadordespesas.services.exceptions.InvalidInputException;
import com.deivinson.gerenciadordespesas.services.exceptions.ResourceNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoryRepository repository;	
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> buscarTodasCategorias(Pageable pageable){
		Page<Category> dto = repository.findAll(pageable);
		return dto.map(x -> new CategoryDTO(x));
	}
	
	@Transactional
    public CategoryDTO criarCategoria(MinCategoryDTO dto) {
		Optional<Category> categoriaExistente = repository.findByNome(dto.getNome());
	    if (categoriaExistente.isPresent()) {
	        System.out.println("Erro: esse nome já existe!");
	        return null;
	    }
		if (dto == null || dto.getNome() == null || dto.getNome().isEmpty()) {
			throw new InvalidInputException("O nome da categoria é obrigatório.");
		}else {
			
		Category categoria = new Category();
        categoria.setNome(dto.getNome());

        categoria = repository.save(categoria);
        return new CategoryDTO(categoria);
		
		}
    }
	
	@Transactional
    public CategoryDTO atualizarNomeCategoria(Long categoriaId, MinCategoryDTO dto) {
		if (dto == null || dto.getNome() == null || dto.getNome().isEmpty()) {
	        throw new InvalidInputException("O nome da categoria é obrigatório.");
	    }
        Category categoria = repository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        categoria.setNome(dto.getNome());

        categoria = repository.save(categoria);
        return new CategoryDTO(categoria);
    }
	
	@Transactional
	public void deletarCategoria(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
