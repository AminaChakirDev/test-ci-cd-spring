package org.wildcodeschool.myblog.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.wildcodeschool.myblog.dto.CategoryDTO;
import org.wildcodeschool.myblog.mapper.CategoryMapper;
import org.wildcodeschool.myblog.model.Category;
import org.wildcodeschool.myblog.security.JwtService;
import org.wildcodeschool.myblog.service.CategoryService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CategoryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void testGetAllCategories() throws Exception {
        // Arrange
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName("Category 1");

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2L);
        category2.setName("Category 2");

        when(categoryService.getAllCategories()).thenReturn(List.of(category1, category2));

        // Act & Assert
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
    }

    @Test
    void testGetCategoryById_CategoryExists() throws Exception {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Category 1");

        when(categoryService.getCategoryById(1L)).thenReturn(categoryDTO);

        // Act & Assert
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category 1"));
    }
}
