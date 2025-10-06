package com.sr.inventory_tracker.controller;

import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.model.SupplierDTO;
import com.sr.inventory_tracker.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@Slf4j
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Operation(summary = "Get Supplier by ID", description = "Retrieve a supplier by its ID", tags = {"Supplier"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier found"),
            @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    @GetMapping("/{id}")
    public SupplierDTO getSupplier(@PathVariable Long id) throws SupplierNotFoundException {
        log.info("Inside getSupplier method of SupplierController - Getting supplier with ID: {}", id);
        return supplierService.getSupplier(id);
    }

    @Operation(summary = "Get All Suppliers", description = "Retrieve a list of all suppliers", tags = {"Supplier"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of suppliers found")
    })
    @GetMapping
    public List<SupplierDTO> getAllSuppliers() {
        log.info("Inside getAllSuppliers method of SupplierController - Getting all suppliers");
        return supplierService.getAllSuppliers();
    }
}
