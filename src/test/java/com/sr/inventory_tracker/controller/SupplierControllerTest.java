package com.sr.inventory_tracker.controller;

import com.sr.inventory_tracker.model.SupplierDTO;
import com.sr.inventory_tracker.service.SupplierService;
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
public class SupplierControllerTest {

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(supplierController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetSupplier() throws Exception {
        SupplierDTO supplierDTO = new SupplierDTO(1L, "Best Supplier");

        when(supplierService.getSupplier(1L)).thenReturn(supplierDTO);

        mockMvc.perform(get("/supplier/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Best Supplier"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetAllSuppliers() throws Exception {
        List<SupplierDTO> supplierList = Arrays.asList(
                new SupplierDTO(1L, "Best Supplier"),
                new SupplierDTO(2L, "Reliable Supplier")
        );

        when(supplierService.getAllSuppliers()).thenReturn(supplierList);

        mockMvc.perform(get("/supplier"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Best Supplier"))
                .andExpect(jsonPath("$[1].name").value("Reliable Supplier"));
    }
}
