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

import java.math.BigDecimal;
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

import com.deivinson.gerenciadordespesas.dto.ExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.InsertExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.TotalExpenseDTO;
import com.deivinson.gerenciadordespesas.dto.UpdateExpenseDTO;
import com.deivinson.gerenciadordespesas.services.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService despesaService;

    @Test
    public void searchExpensesByFiltersShouldRetornarStatusCode200AndPageDespesaDTO() throws Exception {
        Page<ExpenseDTO> pageExpenseDTO = new PageImpl<>(Collections.emptyList());
        when(despesaService.searchExpensesByFilters(anyLong(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(pageExpenseDTO);

        mockMvc.perform(get("/expenses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void calculateTotalExpensesByFilterShouldReturnStatusCode200AndTotalExpenseDTO() throws Exception {
        BigDecimal totalExpenses = new BigDecimal(1000.0);
        
        TotalExpenseDTO totalExpenseDTO = new TotalExpenseDTO();
        totalExpenseDTO.setTotalExpenses(totalExpenses);
        
        when(despesaService.calculateTotalExpensesByFilter(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(totalExpenses);

        mockMvc.perform(get("/expenses/total-expenses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void insertExpenseShouldReturnStatusCode201AndBodyInsertExpenseDTO() throws Exception {
        InsertExpenseDTO insertExpenseDTO = new InsertExpenseDTO();
        insertExpenseDTO.setId(1L);
        when(despesaService.insertExpense(any(InsertExpenseDTO.class))).thenReturn(insertExpenseDTO);

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(insertExpenseDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void updateExpenseShouldReturnStatusCode200AndBodyExpenseDTO() throws Exception {
        Long expenseId = 1L;
        UpdateExpenseDTO insertExpenseDTO = new UpdateExpenseDTO();
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setId(1L);
        when(despesaService.updateExpense(eq(expenseId), any(UpdateExpenseDTO.class))).thenReturn(expenseDTO);

        mockMvc.perform(put("/expenses/{expenseId}", expenseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(insertExpenseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void deleteExpenseShouldReturnStatusCode204() throws Exception {
        Long expenseId = 1L;

        mockMvc.perform(delete("/expenses/{expenseId}", expenseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(despesaService, times(1)).deleteExpense(expenseId);
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
