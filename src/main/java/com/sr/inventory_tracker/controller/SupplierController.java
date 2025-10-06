package com.sr.inventory_tracker.controller;

import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.model.SupplierDTO;
import com.sr.inventory_tracker.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@Slf4j
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public SupplierDTO getSupplier(@PathVariable Long id) throws SupplierNotFoundException {
        log.info("Inside getSupplier method of SupplierController - Getting supplier with ID: {}", id);
        return supplierService.getSupplier(id);
    }

    @GetMapping
    public List<SupplierDTO> getAllSuppliers() {
        log.info("Inside getAllSuppliers method of SupplierController - Getting all suppliers");
        return supplierService.getAllSuppliers();
    }
}
