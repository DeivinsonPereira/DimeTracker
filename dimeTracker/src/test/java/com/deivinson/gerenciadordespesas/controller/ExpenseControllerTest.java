package com.deivinson.gerenciadordespesas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.deivinson.gerenciadordespesas.dto.UpdateExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.ExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.InsertExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.TotalExpenseDTO;
import com.deivinson.gerenciadordespesas.services.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService despesaService;

    @Test
    public void buscarDespesas_DeveRetornarStatusCode200EPageDeDespesaDTO() throws Exception {
        Page<ExpenseDTO> pageDespesaDTO = new PageImpl<>(Collections.emptyList());
        when(despesaService.buscarDespesasPorFiltros(anyLong(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(pageDespesaDTO);

        mockMvc.perform(get("/despesas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void calculaTotalDespesasPorFiltros_DeveRetornarStatusCode200ETotalDespesaDTO() throws Exception {
        Double totalDespesas = 1000.0;
        
        TotalExpenseDTO totalDespesaDTO = new TotalExpenseDTO();
        totalDespesaDTO.setTotalDespesas(totalDespesas);
        
        when(despesaService.calcularTotalDespesasComFiltros(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(totalDespesas);

        mockMvc.perform(get("/despesas/total-despesas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void insert_DeveRetornarStatusCode201EBodyDeDespesaInserirDTO() throws Exception {
        InsertExpenseDTO despesaInserirDTO = new InsertExpenseDTO();
        despesaInserirDTO.setId(1L);
        when(despesaService.insert(any(InsertExpenseDTO.class))).thenReturn(despesaInserirDTO);

        mockMvc.perform(post("/despesas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(despesaInserirDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void atualizarDespesa_DeveRetornarStatusCode200EBodyDeDespesaDTO() throws Exception {
        Long despesaId = 1L;
        UpdateExpenseDTO atualizaDespesaDTO = new UpdateExpenseDTO();
        ExpenseDTO despesaDTO = new ExpenseDTO();
        despesaDTO.setId(1L);
        when(despesaService.atualizarDespesa(eq(despesaId), any(UpdateExpenseDTO.class))).thenReturn(despesaDTO);

        mockMvc.perform(put("/despesas/{despesaId}", despesaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(atualizaDespesaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void deletarDespesa_DeveRetornarStatusCode204() throws Exception {
        Long despesaId = 1L;

        mockMvc.perform(delete("/despesas/{despesaId}", despesaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(despesaService, times(1)).deletarDespesa(despesaId);
    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
