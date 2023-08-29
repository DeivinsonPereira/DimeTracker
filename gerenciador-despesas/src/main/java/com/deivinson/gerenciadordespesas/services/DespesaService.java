package com.deivinson.gerenciadordespesas.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaInserirDTO;
import com.deivinson.gerenciadordespesas.dto.TotalDespesaCatDataDTO;
import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;
import com.deivinson.gerenciadordespesas.respositories.CategoriaRepository;
import com.deivinson.gerenciadordespesas.respositories.DespesaRepository;
import com.deivinson.gerenciadordespesas.respositories.UsuarioRepository;

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
	
	@Transactional
	public DespesaInserirDTO insert(DespesaInserirDTO dto) {
		Despesa entity = new Despesa();
		copyEntity(dto, entity);
		entity = repository.save(entity);
		return new DespesaInserirDTO(entity);
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
