package com.deivinson.dimeTracker.controller;

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

import com.deivinson.dimeTracker.dto.CategoryDTO;
import com.deivinson.dimeTracker.dto.MinCategoryDTO;
import com.deivinson.dimeTracker.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void searchAllCategoriesShouldReturnStatusCode200AndPageCategoryDTO() throws Exception {
        Page<CategoryDTO> pageCategoryDTO = new PageImpl<>(Collections.emptyList());
        when(categoryService.searchAllCategories(any(Pageable.class))).thenReturn(pageCategoryDTO);

        mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void insertCategoryShouldReturnStatusCode201AndBodyCategoryDTO() throws Exception {
        MinCategoryDTO minCategoryDTO = new MinCategoryDTO("New Category");
        CategoryDTO categoryDTO = new CategoryDTO(1L, "New Category");
        when(categoryService.insertCategory(any(MinCategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(minCategoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    public void updateNameCategoryShouldReturnStatusCode200AndBodyCategoryDTO() throws Exception {
        Long categoryId = 1L;
        MinCategoryDTO minCategoryDTO = new MinCategoryDTO("New Name");
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, "New Name");
        when(categoryService.updateNameCategory(eq(categoryId), any(MinCategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(put("/categories/{categoryId}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(minCategoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId))
                .andExpect(jsonPath("$.name").value("New Name"));
    }

    @Test
    public void deleteCategoryShouldRetornarStatusCode204() throws Exception {
        Long categoryId = 1L;

        mockMvc.perform(delete("/categories/{categoryId}", categoryId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategory(categoryId);
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
