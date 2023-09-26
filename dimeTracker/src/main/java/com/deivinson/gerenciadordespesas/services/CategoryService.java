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
public class CategoryService {

	@Autowired
	private CategoryRepository repository;	
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> searchAllCategories(Pageable pageable){
		Page<Category> dto = repository.findAll(pageable);
		return dto.map(x -> new CategoryDTO(x));
	}
	
	@Transactional
    public CategoryDTO insertCategory(MinCategoryDTO dto) {
		Optional<Category> existingCategory = repository.findByName(dto.getName());
	    if (existingCategory.isPresent()) {
	        System.out.println("Error: this name already exists!");
	        return null;
	    }
		if (dto == null || dto.getName() == null || dto.getName().isEmpty()) {
			throw new InvalidInputException("The category name is required.");
		}else {
			
		Category category = new Category();
		category.setName(dto.getName());

		category = repository.save(category);
        return new CategoryDTO(category);
		
		}
    }
	
	@Transactional
    public CategoryDTO updateNameCategory(Long categoryId, MinCategoryDTO dto) {
		if (dto == null || dto.getName() == null || dto.getName().isEmpty()) {
	        throw new InvalidInputException("Category name is required.");
	    }
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(dto.getName());

        category = repository.save(category);
        return new CategoryDTO(category);
    }
	
	@Transactional
	public void deleteCategory(Long id) {
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
