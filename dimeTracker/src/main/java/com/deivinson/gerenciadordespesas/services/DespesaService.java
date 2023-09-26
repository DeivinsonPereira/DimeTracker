package com.deivinson.gerenciadordespesas.services;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deivinson.gerenciadordespesas.dto.ExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.InsertExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.UpdateExpenseDTO;
import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.entities.User;
import com.deivinson.gerenciadordespesas.repositories.CategoryRepository;
import com.deivinson.gerenciadordespesas.repositories.ExpenseRepository;
import com.deivinson.gerenciadordespesas.repositories.UserRepository;
import com.deivinson.gerenciadordespesas.services.exceptions.DataInvalidaException;
import com.deivinson.gerenciadordespesas.services.exceptions.ResourceNotFoundException;

@Service
public class DespesaService {

	@Autowired
	private ExpenseRepository repository;
	
	@Autowired
	private CategoryRepository categoriaRepository;
	
	@Autowired
	private UserRepository usuarioRepository;
	
	
	@Transactional(readOnly = true)
	public Page<ExpenseDTO> buscarDespesasPorFiltros(Long categoriaId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        Page<Expense> despesas = null;

        if (categoriaId != null && dataInicio != null && dataFim != null) {
            if (dataInicio.isAfter(dataFim)) {
                throw new DataInvalidaException("Data final não pode ser anterior à data de início.");
            }
            despesas = repository.findByCategoriaIdAndDataBetween(categoriaId, dataInicio, dataFim, pageable);
        } else if (categoriaId != null) {
            despesas = repository.findByCategoriaId(categoriaId, pageable);
        } else if (dataInicio != null && dataFim != null) {
            if (dataInicio.isAfter(dataFim)) {
                throw new DataInvalidaException("Data final não pode ser anterior à data de início.");
            }
            despesas = repository.findByDataBetween(dataInicio, dataFim, pageable);
        } else {
            despesas = repository.findAllWithCategoria(pageable);
        }

        return despesas.map(x -> new ExpenseDTO(x));
    }
	
	@Transactional(readOnly = true)
	public BigDecimal calcularTotalDespesasComFiltros(Long categoriaId, LocalDate dataInicio, LocalDate dataFim) {
        if (categoriaId != null && dataInicio != null && dataFim != null) {
            Category categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

            return repository.calcularValorTotalDespesasPorCategoriaEData(categoria, dataInicio, dataFim);
        } else if (categoriaId != null) {
            Category categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

            return repository.calcularDespesaTotalPorCategoria(categoria);
        } else if (dataInicio != null && dataFim != null) {
            return repository.calcularSomaTotalDespesasPorPeriodo(dataInicio, dataFim);
        } else {
            return repository.calcularDespesaTotal();
        }
    }
	
	@Transactional
	public InsertExpenseDTO insert(InsertExpenseDTO dto) {
		Expense entity = new Expense();
		copyEntity(dto, entity);
		entity = repository.save(entity);
		return new InsertExpenseDTO(entity);
	}
	
	@Transactional
    public ExpenseDTO atualizarDespesa(Long despesaId, UpdateExpenseDTO dto) {
        try {
        	Expense despesa = repository.getReferenceById(despesaId);
        	
        	if (dto.getValor() != null) {
        		despesa.setValor(dto.getValor());
        	}
        	if (dto.getData() != null) {
        		despesa.setData(dto.getData());
        	}
        	if (dto.getCategoriaId() != null) {
        		Category categoria = categoriaRepository.findById(dto.getCategoriaId())
        				.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        		despesa.setCategoria(categoria);
        	}
        	
        	despesa = repository.save(despesa);
        	return new ExpenseDTO(despesa);
        	
        }
	
        catch (EntityNotFoundException e) {
        	throw new ResourceNotFoundException("Id not found " + despesaId);
        }
    }
	
	@Transactional
    public void deletarDespesa(Long despesaId) {
        Expense despesa = repository.findById(despesaId)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));

        repository.delete(despesa);
    }
	
	private void copyEntity(InsertExpenseDTO dto, Expense entity) {
		entity.setData(dto.getData());
		entity.setValor(dto.getValor());
		Category categoria = categoriaRepository.findById(dto.getCategoriaId())
	            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
	    entity.setCategoria(categoria);
	    User usuario = usuarioRepository.findById(dto.getUsuarioId())
	            .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
	    entity.setUsuario(usuario);
	    
	}
}
