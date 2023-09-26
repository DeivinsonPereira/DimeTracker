package com.deivinson.gerenciadordespesas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.deivinson.gerenciadordespesas.dto.UpdateExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.ExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.InsertExpenseDTO;
import com.deivinson.gerenciadordespesas.entities.Category;
import com.deivinson.gerenciadordespesas.entities.Expense;
import com.deivinson.gerenciadordespesas.entities.User;
import com.deivinson.gerenciadordespesas.repositories.CategoriaRepository;
import com.deivinson.gerenciadordespesas.repositories.DespesaRepository;
import com.deivinson.gerenciadordespesas.repositories.UsuarioRepository;
import com.deivinson.gerenciadordespesas.services.exceptions.DataInvalidaException;
import com.deivinson.gerenciadordespesas.services.exceptions.ResourceNotFoundException;
import com.deivinson.gerenciadordespesas.tests.Factory;

@ExtendWith(SpringExtension.class)
public class DespesaServiceTest {

	@InjectMocks
	private DespesaService service;
	
	@Mock
	private DespesaRepository repository;
	
    @Mock
    private CategoriaRepository categoriaRepository;
    
    @Mock
    private UsuarioRepository usuarioRepository;
	
	private Long categoriaId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Expense despesa1;
    private Expense despesa2;
    private Expense despesa3;
    
    private Page<Expense> page;
    
    private List<Expense> despesas;

	@BeforeEach
	void setUp() throws Exception {
		
		despesa1 = Factory.construtorDespesaVazio();
		despesa1.setId(1L);
		despesa1.setValor(100.00);
		despesa1.setData(dataFim);

		despesa2 = Factory.construtorDespesaVazio();
		despesa1.setId(2L);
		despesa1.setValor(200.00);
		despesa1.setData(dataFim);

		despesa3 = Factory.construtorDespesaVazio();
		despesa1.setId(3L);
		despesa1.setValor(300.00);
		despesa1.setData(dataFim);
		
		despesas = Arrays.asList(despesa1, despesa2, despesa3);
		
		page = new PageImpl<>(despesas);
		
		categoriaId = 1L;
        dataInicio = LocalDate.of(2023, 1, 1);
        dataFim = LocalDate.of(2023, 12, 31);
        
        
	}
	
	@Test
	public void buscarDespesasPorFiltrosShouldGetExpenseByCategoriaIdWhenCategoriaIdExists() {
		
	    when(repository.findAllWithCategoria(any(Pageable.class))).thenReturn(page);

	    Page<ExpenseDTO> despesasDTO = service.buscarDespesasPorFiltros(null, null, null, Pageable.unpaged());

	    verify(repository).findAllWithCategoria(Pageable.unpaged());

	    List<ExpenseDTO> listdespesasDTO = despesas.stream()
	            .map(despesa -> new ExpenseDTO(despesa))
	            .collect(Collectors.toList());
	    assertEquals(new PageImpl<>(listdespesasDTO), despesasDTO);
	}
	
	@Test
    public void testBuscarDespesasPorFiltrosCategoriaId() {
		 
        when(repository.findByCategoriaId(eq(categoriaId), any(Pageable.class))).thenReturn(page);

        Page<ExpenseDTO> despesasDTO = service.buscarDespesasPorFiltros(categoriaId, null, null, Pageable.unpaged());

        verify(repository).findByCategoriaId(eq(categoriaId), any(Pageable.class));
        
        List<ExpenseDTO> despesasDTOMockadas = despesas.stream()
                .map(despesa -> new ExpenseDTO(despesa))
                .collect(Collectors.toList());
        assertEquals(new PageImpl<>(despesasDTOMockadas), despesasDTO);
    }
	 
	@Test
	public void testBuscarDespesasPorFiltrosDatas() {

	    when(repository.findByDataBetween(eq(dataInicio), eq(dataFim), any(Pageable.class))).thenReturn(page);

	    Page<ExpenseDTO> despesasDTO = service.buscarDespesasPorFiltros(null, dataInicio, dataFim, Pageable.unpaged());

	    verify(repository).findByDataBetween(eq(dataInicio), eq(dataFim), any(Pageable.class));

	    List<ExpenseDTO> despesasDTOMockadas = despesas.stream()
	            .map(despesa -> new ExpenseDTO(despesa))
	            .collect(Collectors.toList());
	    assertEquals(new PageImpl<>(despesasDTOMockadas), despesasDTO);
	}

	
	@Test
	public void testBuscarDespesasPorFiltrosCategoriaIdEData() {

	    when(repository.findByCategoriaIdAndDataBetween(eq(categoriaId), eq(dataInicio), eq(dataFim), any(Pageable.class)))
	            .thenReturn(page);

	    Page<ExpenseDTO> despesasDTO = service.buscarDespesasPorFiltros(categoriaId, dataInicio, dataFim,
	            Pageable.unpaged());

	    verify(repository).findByCategoriaIdAndDataBetween(eq(categoriaId), eq(dataInicio), eq(dataFim),
	            any(Pageable.class));

	    List<ExpenseDTO> despesasDTOMockadas = despesas.stream()
	            .map(despesa -> new ExpenseDTO(despesa))
	            .collect(Collectors.toList());
	    assertEquals(new PageImpl<>(despesasDTOMockadas), despesasDTO);
	}
	
	@Test
    public void testBuscarDespesasPorFiltros() {
		
        Category categoria = Factory.construtorCategoriaVazio();
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
        Pageable pageable = Pageable.unpaged();
        Category categoria = new Category(1L, "Alimentação");
        List<Expense> despesas = new ArrayList<>();
        despesas.add(new Expense(1L, 50.0, LocalDate.of(2023, 1, 15), null, categoria));
        despesas.add(new Expense(2L, 30.0, LocalDate.of(2023, 1, 20), null, categoria));
        Page<Expense> page = new PageImpl<>(despesas);

        when(repository.findAllWithCategoria(pageable)).thenReturn(page);

        Page<ExpenseDTO> resultado = service.buscarDespesasPorFiltros(null, null, null, pageable);

        assertEquals(2, resultado.getTotalElements());
    }
	
	@Test
    public void testBuscarDespesasPorFiltrosDatasInvalidas() {
        LocalDate dataInicio = LocalDate.of(2023, 2, 1);
        LocalDate dataFim = LocalDate.of(2023, 1, 1);

        assertThrows(DataInvalidaException.class, () -> service.buscarDespesasPorFiltros(null, dataInicio, dataFim, Pageable.unpaged()));
    }
	
	@Test
	public void testBuscarDespesasPorFiltrosDataFinalAnteriorADataInicio() {
	    LocalDate dataInicio = LocalDate.of(2023, 2, 15);
	    LocalDate dataFim = LocalDate.of(2023, 2, 10); 

	    assertThrows(DataInvalidaException.class, () -> {
	        service.buscarDespesasPorFiltros(null, dataInicio, dataFim, Pageable.unpaged());
	    });
	}
	
	@Test
	public void testBuscarDespesasPorFiltrosCategoriaSemDespesas() {
	    Long categoriaId = 1L;

	    when(repository.findByCategoriaId(eq(categoriaId), any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

	    Page<ExpenseDTO> despesasDTO = service.buscarDespesasPorFiltros(categoriaId, null, null, Pageable.unpaged());

	    verify(repository).findByCategoriaId(eq(categoriaId), any(Pageable.class));

	    assertEquals(0, despesasDTO.getTotalElements());
	}
	
	@Test
    public void testBuscarDespesasPorFiltrosDataInvalidaExceptionAndCategoriaIdExists() {
        categoriaId = 1L;
        dataInicio = LocalDate.of(2023, 2, 1);
        dataFim = LocalDate.of(2023, 1, 1);


        assertThrows(DataInvalidaException.class, () -> service.buscarDespesasPorFiltros(categoriaId, dataInicio, dataFim, Pageable.unpaged()));

        verify(repository, never()).findByCategoriaIdAndDataBetween(any(), any(), any(), any());
    }
	
	@Test
    public void testDeletarDespesaExistente() {
        Expense despesa = Factory.construtorDespesaComArgumentos();

        when(repository.findById(1L)).thenReturn(Optional.of(despesa));

        service.deletarDespesa(1L);

        verify(repository).delete(despesa);
    }

	@Test
    public void testDeletarDespesaNaoExistente() {
		Expense despesa = Factory.construtorDespesaComArgumentos();
	 
        when(repository.findById(despesa.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deletarDespesa(1L));
    }
	
	@Test
    public void testInsertDespesa() {
		InsertExpenseDTO dtoEntrada = new InsertExpenseDTO();
		dtoEntrada.setId(categoriaId);
	    dtoEntrada.setValor(100.00);
	    dtoEntrada.setData(LocalDate.of(2023, 10, 15));
	    dtoEntrada.setCategoriaId(1L);
	    dtoEntrada.setUsuarioId(1L);

	    Category categoria = Factory.construtorCategoriaVazio();
	    categoria.setId(1L);
	    when(categoriaRepository.findById(anyLong())).thenReturn(Optional.of(categoria));

	    User usuario = Factory.construtorUsuarioVazio();
	    usuario.setId(1L);
	    when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

	    Expense despesa = Factory.construtorDespesaVazio();
	    despesa.setId(1L);
	    despesa.setValor(100.00);
	    despesa.setData(LocalDate.of(2023, 10, 15));
	    despesa.setCategoria(categoria);
	    despesa.setUsuario(usuario);
	    
	    when(repository.save(any(Expense.class))).thenReturn(despesa);
	    
	    InsertExpenseDTO resultadoDTO = service.insert(dtoEntrada);

	    verify(repository).save(any(Expense.class));
	    

	    assertEquals(despesa.getId(), resultadoDTO.getId());
	    assertEquals(dtoEntrada.getValor(), resultadoDTO.getValor()); 
	    assertEquals(dtoEntrada.getData(), resultadoDTO.getData());
    }
	
	@Test
    public void testAtualizarDespesaSucesso() {
		Long despesaId = 1L;
        UpdateExpenseDTO dto = new UpdateExpenseDTO(100.0, LocalDate.now(), 2L);
        
        Category categoria = new Category();
        categoria.setId(2L);
        
        User usuario = new User();
        usuario.setId(2L);
        
        Expense despesaExistente = new Expense();
        despesaExistente.setId(despesaId);
        despesaExistente.setCategoria(categoria);
        despesaExistente.setUsuario(usuario);

        when(repository.save(any(Expense.class))).thenReturn(despesaExistente);

        when(repository.getReferenceById(despesaId)).thenReturn(despesaExistente);
        when(categoriaRepository.findById(dto.getCategoriaId())).thenReturn(Optional.of(categoria));

        ExpenseDTO resultado = service.atualizarDespesa(despesaId, dto);

        assertNotNull(resultado);
        assertEquals(dto.getValor(), resultado.getValor());
        assertEquals(dto.getData(), resultado.getData());
        assertEquals(categoria.getNome(), resultado.getNomeCategoria());
    }
	
	@Test
	public void testAtualizarDespesaFalhaIdNaoEncontrado() {
		Long despesaId = 1L;
	    UpdateExpenseDTO dto = new UpdateExpenseDTO(100.0, LocalDate.now(), 2L);

	    when(repository.getReferenceById(despesaId)).thenReturn(new Expense()); 
	    when(repository.findById(despesaId)).thenReturn(Optional.empty());

	    assertThrows(ResourceNotFoundException.class, () -> service.atualizarDespesa(despesaId, dto));
	}
	
	@Test
    public void testCopyEntity() throws Exception{
		InsertExpenseDTO dto = new InsertExpenseDTO();
        dto.setData(LocalDate.now());
        dto.setValor(100.0);
        dto.setCategoriaId(1L);
        dto.setUsuarioId(2L);

        Expense entity = new Expense();

        Category categoria = new Category();
        categoria.setId(1L);
        categoria.setNome("Categoria Teste");

        User usuario = new User();
        usuario.setId(2L);
        usuario.setNome("Usuário Teste");

        when(categoriaRepository.findById(dto.getCategoriaId())).thenReturn(Optional.of(categoria));
        when(usuarioRepository.findById(dto.getUsuarioId())).thenReturn(Optional.of(usuario));

        Method privateMethod = DespesaService.class.getDeclaredMethod("copyEntity", InsertExpenseDTO.class, Expense.class);
        privateMethod.setAccessible(true);
        privateMethod.invoke(service, dto, entity);

        assertEquals(dto.getData(), entity.getData());
        assertEquals(dto.getValor(), entity.getValor()); 
        assertEquals(categoria, entity.getCategoria());
        assertEquals(usuario, entity.getUsuario());
    }
	
	@Test
    public void testCopyEntityCategoriaNaoEncontrada() throws Exception {
		
		InsertExpenseDTO dto = new InsertExpenseDTO();
        dto.setCategoriaId(1L);

        Expense entity = new Expense();

        when(categoriaRepository.findById(dto.getCategoriaId())).thenReturn(Optional.empty());

        Method privateMethod = DespesaService.class.getDeclaredMethod("copyEntity", InsertExpenseDTO.class, Expense.class);
        privateMethod.setAccessible(true);

        try {
            privateMethod.invoke(service, dto, entity);
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertEquals(EntityNotFoundException.class, actualException.getClass());
            assertEquals("Categoria não encontrada", actualException.getMessage());
        }
	}

	@Test
    public void testCopyEntityUsuarioNaoEncontrado() throws Exception {
		 
		InsertExpenseDTO dto = new InsertExpenseDTO();
        dto.setUsuarioId(2L); 

        Expense entity = new Expense();

        when(categoriaRepository.findById(dto.getCategoriaId())).thenReturn(Optional.of(new Category()));
        when(usuarioRepository.findById(dto.getUsuarioId())).thenReturn(Optional.empty());

        Method privateMethod = DespesaService.class.getDeclaredMethod("copyEntity", InsertExpenseDTO.class, Expense.class);
        privateMethod.setAccessible(true);

        try {
            privateMethod.invoke(service, dto, entity);
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertEquals(EntityNotFoundException.class, actualException.getClass());
            assertEquals("Usuario não encontrado", actualException.getMessage());
        }
    }
}