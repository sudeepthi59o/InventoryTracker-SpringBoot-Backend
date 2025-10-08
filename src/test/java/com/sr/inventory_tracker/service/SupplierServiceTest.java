package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.model.Supplier;
import com.sr.inventory_tracker.DTO.SupplierDTO;
import com.sr.inventory_tracker.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier supplier;

    @BeforeEach
    public void setUp() {
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("ABC Supplies");

    }

    @Test
    public void testGetSupplier_Success() throws SupplierNotFoundException {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        SupplierDTO result = supplierService.getSupplier(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ABC Supplies", result.getName());
    }

    @Test
    public void testGetSupplier_SupplierNotFound() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        SupplierNotFoundException thrown = assertThrows(SupplierNotFoundException.class, () -> {
            supplierService.getSupplier(1L);
        });

        assertEquals("Supplier with ID 1 not found", thrown.getMessage());
    }

    @Test
    public void testGetAllSuppliers_Success() {
        List<Supplier> suppliers = List.of(supplier);
        when(supplierRepository.findAll()).thenReturn(suppliers);

        List<SupplierDTO> result = supplierService.getAllSuppliers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ABC Supplies", result.get(0).getName());
    }

    @Test
    public void testGetAllSuppliers_EmptyList() {
        when(supplierRepository.findAll()).thenReturn(List.of());

        List<SupplierDTO> result = supplierService.getAllSuppliers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFromEntityListToDTOList() {
        List<Supplier> suppliers = List.of(supplier);

        List<SupplierDTO> result = supplierService.fromEntityListToDTOList(suppliers);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ABC Supplies", result.get(0).getName());
    }

}