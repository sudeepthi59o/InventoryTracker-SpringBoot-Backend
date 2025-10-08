package com.sr.inventory_tracker.controller;

import com.sr.inventory_tracker.DTO.CategoryDTO;
import com.sr.inventory_tracker.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Initialize MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    // Positive test: Get Category by ID (Category exists)
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetCategoryById_Positive() throws Exception {
        // Prepare test data
        CategoryDTO mockCategory = new CategoryDTO(1L, "Electronics");

        // Mock service call
        when(categoryService.getCategory(1L)).thenReturn(mockCategory);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/category/{id}", 1L))
                .andExpect(status().isOk())  // Expect HTTP 200
                .andExpect(jsonPath("$.name").value("Electronics"));  // Check response body
    }


    // Positive test: Get All Categories (Categories exist)
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetAllCategories_Positive() throws Exception {
        // Prepare test data
        List<CategoryDTO> mockCategories = Arrays.asList(
                new CategoryDTO(1L, "Electronics"),
                new CategoryDTO(2L, "Furniture")
        );

        // Mock service call
        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())  // Expect HTTP 200
                .andExpect(jsonPath("$.length()").value(2))  // Check number of categories
                .andExpect(jsonPath("$[0].name").value("Electronics"))  // Check first category name
                .andExpect(jsonPath("$[1].name").value("Furniture"));  // Check second category name
    }

}