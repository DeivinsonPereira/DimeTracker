package com.deivinson.gerenciadordespesas.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deivinson.gerenciadordespesas.dto.AtualizaDespesaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaCategoriaDataInfoDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaInserirDTO;
import com.deivinson.gerenciadordespesas.dto.TotalDespesaCatDataDTO;
import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;
import com.deivinson.gerenciadordespesas.respositories.CategoriaRepository;
import com.deivinson.gerenciadordespesas.respositories.DespesaRepository;
import com.deivinson.gerenciadordespesas.respositories.UsuarioRepository;
import com.deivinson.gerenciadordespesas.services.exceptions.ResourceNotFoundException;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional(readOnly = true)
	public Page<DespesaDTO> buscarTodasDespesas(Pageable pageable){
		Page<Despesa> dto = repository.findAll(pageable);
		return dto.map(x -> new DespesaDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<DespesaDTO> buscarDespesasPorCategoria(Long categoriaId) {
		Categoria categoria = categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
		List<Despesa> despesas = repository.findByCategoria(categoria);
		return despesas.stream().map(x -> new DespesaDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<DespesaDTO> buscarDespesasPorCategoriaEData(Long categoriaId, LocalDate dataInicio,	LocalDate dataFim) {
		Categoria categoria = categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
		List<Despesa> despesas = repository.buscarDespesasPorCategoriaEData(categoria, dataInicio, dataFim);
		return despesas.stream().map(x -> new DespesaDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Double calcularTotalDespesas() {
		return repository.calcularDespesaTotal();
	}
	
	@Transactional(readOnly = true)
	public Double calcularTotalDespesasPorCategoria(Long categoriaId) {
		Categoria categoria = categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
		return repository.calcularDespesaTotalPorCategoria(categoria);
	}
	
	@Transactional(readOnly = true)
	public TotalDespesaCatDataDTO calcularValorTotalDespesasPorCategoriaEData(Long categoriaId, LocalDate dataInicio, LocalDate dataFim) {
		
		Categoria categoria = categoriaRepository.findById(categoriaId)
	            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
		
		Double valorTotal = repository.calcularValorTotalDespesasPorCategoriaEData(categoria, dataInicio, dataFim);
	   
		TotalDespesaCatDataDTO dto = new TotalDespesaCatDataDTO();
        dto.setDataInicio(dataInicio);
        dto.setDataFim(dataFim);
        dto.setCategoria(categoria.getNome());
        dto.setValorTotal(valorTotal);
	    return dto;
	}
	
	@Transactional(readOnly = true)
	public Double calcularSomaTotalDespesasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        Double somaTotal = repository.calcularSomaTotalDespesasPorPeriodo(dataInicio, dataFim);
        return somaTotal != null ? somaTotal : 0.0;
    }
	
	@Transactional(readOnly = true)
	public List<DespesaCategoriaDataInfoDTO> ValorTotalDespesasCategoriaData(LocalDate dataInicio, LocalDate dataFim) {
        List<Despesa> despesas = repository.findByDataBetween(dataInicio, dataFim);
        List<DespesaCategoriaDataInfoDTO> despesasDTO = new ArrayList<>();

        for (Despesa despesa : despesas) {
        	DespesaCategoriaDataInfoDTO dto = new DespesaCategoriaDataInfoDTO();
            dto.setId(despesa.getId());
            dto.setValor(despesa.getValor());
            dto.setData(despesa.getData());
            dto.setCategoria(despesa.getCategoria().getNome()); // Supondo que a categoria tenha um atributo "nome"
            despesasDTO.add(dto);
        }

        return despesasDTO;
    }
	
	@Transactional
	public DespesaInserirDTO insert(DespesaInserirDTO dto) {
		Despesa entity = new Despesa();
		copyEntity(dto, entity);
		entity = repository.save(entity);
		return new DespesaInserirDTO(entity);
	}
	
	@Transactional
    public DespesaDTO atualizarDespesa(Long despesaId, AtualizaDespesaDTO dto) {
        try {
        	Despesa despesa = repository.getReferenceById(despesaId);
        	
        	if (dto.getValor() != null) {
        		despesa.setValor(dto.getValor());
        	}
        	if (dto.getData() != null) {
        		despesa.setData(dto.getData());
        	}
        	if (dto.getCategoriaId() != null) {
        		Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
        				.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        		despesa.setCategoria(categoria);
        	}
        	
        	despesa = repository.save(despesa);
        	return new DespesaDTO(despesa);
        	
        }
	
        catch (EntityNotFoundException e) {
        	throw new ResourceNotFoundException("Id not found " + despesaId);
        }
    }
	
	@Transactional
    public void deletarDespesa(Long despesaId) {
        Despesa despesa = repository.findById(despesaId)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));

        repository.delete(despesa);
    }
	
	private void copyEntity(DespesaInserirDTO dto, Despesa entity) {
		entity.setData(dto.getData());
		entity.setValor(dto.getValor());
		Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
	            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
	    entity.setCategoria(categoria);
	    Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
	            .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
	    entity.setUsuario(usuario);
	    
	}
	
}
