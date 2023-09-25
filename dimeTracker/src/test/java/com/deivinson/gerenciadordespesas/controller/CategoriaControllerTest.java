package com.deivinson.gerenciadordespesas.controller;

import static org.mockito.ArgumentMatchers.any;
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

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.deivinson.gerenciadordespesas.dto.CategoriaDTO;
import com.deivinson.gerenciadordespesas.dto.MinCategoriaDTO;
import com.deivinson.gerenciadordespesas.services.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Test
    public void buscarTodasCategorias_DeveRetornarStatusCode200EPageDeCategoriasDTO() throws Exception {
        Page<CategoriaDTO> pageCategoriaDTO = new PageImpl<>(Collections.emptyList());
        when(categoriaService.buscarTodasCategorias(any(Pageable.class))).thenReturn(pageCategoriaDTO);

        mockMvc.perform(get("/categorias")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void criarCategoria_DeveRetornarStatusCode201EBodyDeCategoriaDTO() throws Exception {
        MinCategoriaDTO minCategoriaDTO = new MinCategoriaDTO("Nova Categoria");
        CategoriaDTO categoriaDTO = new CategoriaDTO(1L, "Nova Categoria");
        when(categoriaService.criarCategoria(any(MinCategoriaDTO.class))).thenReturn(categoriaDTO);

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(minCategoriaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Nova Categoria"));
    }

    @Test
    public void atualizarNomeCategoria_DeveRetornarStatusCode200EBodyDeCategoriaDTO() throws Exception {
        Long categoriaId = 1L;
        MinCategoriaDTO minCategoriaDTO = new MinCategoriaDTO("Novo Nome");
        CategoriaDTO categoriaDTO = new CategoriaDTO(categoriaId, "Novo Nome");
        when(categoriaService.atualizarNomeCategoria(eq(categoriaId), any(MinCategoriaDTO.class))).thenReturn(categoriaDTO);

        mockMvc.perform(put("/categorias/{categoriaId}", categoriaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(minCategoriaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoriaId))
                .andExpect(jsonPath("$.nome").value("Novo Nome"));
    }

    @Test
    public void deletarCategoria_DeveRetornarStatusCode204() throws Exception {
        Long categoriaId = 1L;

        mockMvc.perform(delete("/categorias/{categoriaId}", categoriaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(categoriaService, times(1)).deletarCategoria(categoriaId);
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
