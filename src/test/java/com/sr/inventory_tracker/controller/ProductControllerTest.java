package com.sr.inventory_tracker.controller;

import com.sr.inventory_tracker.model.*;
import com.sr.inventory_tracker.repository.ProductRepository;
import com.sr.inventory_tracker.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }


    @Test
    void testGetProductById() throws Exception {
        ProductDTO productDTO = new ProductDTO(1L, "Laptop", 1000.0, 50, new SupplierDTO(1L, "Best Supplier"), new CategoryDTO(1L, "Electronics"));
        when(productService.getProduct(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1000.0));
    }

    @Test
    void testGetAllProducts() throws Exception {
        ProductDTO productDTO = new ProductDTO(1L, "Laptop", 1000.0, 50, new SupplierDTO(1L, "Best Supplier"), new CategoryDTO(1L, "Electronics"));
        when(productService.getAllProducts()).thenReturn(List.of(productDTO));

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].price").value(1000.0));
    }
}
