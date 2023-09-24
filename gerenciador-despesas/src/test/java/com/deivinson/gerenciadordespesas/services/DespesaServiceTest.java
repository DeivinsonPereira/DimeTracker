package com.deivinson.gerenciadordespesas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.deivinson.gerenciadordespesas.dto.DespesaDTO;
import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.repositories.CategoriaRepository;
import com.deivinson.gerenciadordespesas.repositories.DespesaRepository;
import com.deivinson.gerenciadordespesas.tests.Factory;

@ExtendWith(SpringExtension.class)
public class DespesaServiceTest {

	@InjectMocks
	private DespesaService service;
	
	@Mock
	private DespesaRepository repository;
	
    @Mock
    private CategoriaRepository categoriaRepository;
    
	
	private Long categoriaId;
    private LocalDate dataInicio;
    private LocalDate dataFim;

	@BeforeEach
	void setUp() throws Exception {
		
		categoriaId = 1L;
        dataInicio = LocalDate.of(2023, 1, 1);
        dataFim = LocalDate.of(2023, 12, 31);
	}
	
	@Test
    public void testBuscarDespesasPorFiltros() {
		
        Categoria categoria = Factory.construtorCategoriaVazio();
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));

        when(repository.calcularValorTotalDespesasPorCategoriaEData(categoria, dataInicio, dataFim)).thenReturn(100.0);
        when(repository.calcularDespesaTotalPorCategoria(categoria)).thenReturn(50.0);
        when(repository.calcularSomaTotalDespesasPorPeriodo(dataInicio, dataFim)).thenReturn(200.0);
        when(repository.calcularDespesaTotal()).thenReturn(300.0);

        Double result1 = service.calcularTotalDespesasComFiltros(categoriaId, dataInicio, dataFim);
        Double result2 = service.calcularTotalDespesasComFiltros(categoriaId, null, null);
        Double result3 = service.calcularTotalDespesasComFiltros(null, dataInicio, dataFim);
        Double result4 = service.calcularTotalDespesasComFiltros(null, null, null);

        assertEquals(100.0, result1);
        assertEquals(50.0, result2);
        assertEquals(200.0, result3);
        assertEquals(300.0, result4);
    }
	
	 @Test
	    public void testBuscarDespesasPorFiltrosSemFiltros() {
	        // Não fornecer filtros, deve buscar todas as despesas
	        Pageable pageable = Pageable.unpaged();
	        Categoria categoria = new Categoria(1L, "Alimentação");
	        List<Despesa> despesas = new ArrayList<>();
	        despesas.add(new Despesa(1L, 50.0, LocalDate.of(2023, 1, 15), null, categoria));
	        despesas.add(new Despesa(2L, 30.0, LocalDate.of(2023, 1, 20), null, categoria));
	        Page<Despesa> page = new PageImpl<>(despesas);

	        // Simular comportamento dos repositórios
	        when(repository.findAllWithCategoria(pageable)).thenReturn(page);

	        // Chamar o método sem filtros
	        Page<DespesaDTO> resultado = service.buscarDespesasPorFiltros(null, null, null, pageable);

	        // Verificar se o resultado é o esperado
	        assertEquals(2, resultado.getTotalElements());
	        // Adicione mais verificações conforme necessário
	    }
	  
}