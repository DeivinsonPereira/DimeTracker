package com.deivinson.gerenciadordespesas.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deivinson.gerenciadordespesas.dto.CategoriaDTO;
import com.deivinson.gerenciadordespesas.dto.MinCategoriaDTO;
import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.repositories.CategoriaRepository;
import com.deivinson.gerenciadordespesas.services.exceptions.DatabaseException;
import com.deivinson.gerenciadordespesas.services.exceptions.ResourceNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;	
	
	@Transactional(readOnly = true)
	public Page<CategoriaDTO> buscarTodasCategorias(Pageable pageable){
		Page<Categoria> dto = repository.findAll(pageable);
		return dto.map(x -> new CategoriaDTO(x));
	}
	
	@Transactional
    public CategoriaDTO criarCategoria(MinCategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());

        categoria = repository.save(categoria);
        return new CategoriaDTO(categoria);
    }
	
	@Transactional
    public CategoriaDTO atualizarNomeCategoria(Long categoriaId, MinCategoriaDTO dto) {
        Categoria categoria = repository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        categoria.setNome(dto.getNome());

        categoria = repository.save(categoria);
        return new CategoriaDTO(categoria);
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
