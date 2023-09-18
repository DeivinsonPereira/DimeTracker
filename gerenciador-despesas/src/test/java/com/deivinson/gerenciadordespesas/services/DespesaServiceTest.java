package com.deivinson.gerenciadordespesas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.deivinson.gerenciadordespesas.entities.Categoria;
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
    public void testCalcularTotalDespesasComFiltros() {

		Long categoriaId = 1L;
        LocalDate dataInicio = LocalDate.of(2023, 1, 1);
        LocalDate dataFim = LocalDate.of(2023, 12, 31);

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
    public void testCalcularTotalDespesasComFiltrosCategoriaNaoEncontrada() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.calcularTotalDespesasComFiltros(categoriaId, dataInicio, dataFim));
    }
	  
}