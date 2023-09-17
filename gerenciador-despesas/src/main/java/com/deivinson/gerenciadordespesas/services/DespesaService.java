package com.deivinson.gerenciadordespesas.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deivinson.gerenciadordespesas.dto.AtualizaDespesaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.dto.DespesaInserirDTO;
import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;
import com.deivinson.gerenciadordespesas.repositories.CategoriaRepository;
import com.deivinson.gerenciadordespesas.repositories.DespesaRepository;
import com.deivinson.gerenciadordespesas.repositories.UsuarioRepository;
import com.deivinson.gerenciadordespesas.services.exceptions.ResourceNotFoundException;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Transactional(readOnly = true)
	public Page<DespesaDTO> buscarDespesasPorFiltros(Long categoriaId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Despesa> cq = cb.createQuery(Despesa.class);
	    Root<Despesa> despesaRoot = cq.from(Despesa.class);

	    despesaRoot.fetch("categoria", JoinType.LEFT);

	    List<Predicate> predicates = new ArrayList<>();

	    if (categoriaId != null) {
	        predicates.add(cb.equal(despesaRoot.get("categoria").get("id"), categoriaId));
	    }

	    if (dataInicio != null) {
	        predicates.add(cb.greaterThanOrEqualTo(despesaRoot.get("data"), dataInicio));
	    }

	    if (dataFim != null) {
	        predicates.add(cb.lessThanOrEqualTo(despesaRoot.get("data"), dataFim));
	    }

	    cq.where(predicates.toArray(new Predicate[0]));

	    TypedQuery<Despesa> query = entityManager.createQuery(cq)
	            .setFirstResult((int) pageable.getOffset())
	            .setMaxResults(pageable.getPageSize());

	    List<Despesa> despesas = query.getResultList();
	    return new PageImpl<>(despesas.stream().map(DespesaDTO::new).collect(Collectors.toList()), pageable, despesas.size());
	}
	
	@Transactional(readOnly = true)
	public Double calcularTotalDespesasComFiltros(Long categoriaId, LocalDate dataInicio, LocalDate dataFim) {
        if (categoriaId != null && dataInicio != null && dataFim != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

            return repository.calcularValorTotalDespesasPorCategoriaEData(categoria, dataInicio, dataFim);
        } else if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

            return repository.calcularDespesaTotalPorCategoria(categoria);
        } else if (dataInicio != null && dataFim != null) {
            return repository.calcularSomaTotalDespesasPorPeriodo(dataInicio, dataFim);
        } else {
            return repository.calcularDespesaTotal();
        }
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
