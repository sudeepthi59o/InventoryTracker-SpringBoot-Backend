package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.model.Supplier;
import com.sr.inventory_tracker.model.SupplierDTO;
import com.sr.inventory_tracker.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService{

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierDTO getSupplier(Long id) throws SupplierNotFoundException {
        log.info("Inside getSupplier method - Fetching supplier with ID: {}", id);

        Optional<Supplier> supplier = supplierRepository.findById(id);

        if (supplier.isEmpty()) {
            log.error("Supplier with ID {} not found", id);
            throw new SupplierNotFoundException("Supplier with ID " + id + " not found");
        }

        return SupplierDTO.builder()
                .id(supplier.get().getId())
                .name(supplier.get().getName())
                .build();

    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        log.info("Inside getAllSuppliers method - Fetching all suppliers");

        List<Supplier> suppliers = supplierRepository.findAll();

        if (suppliers.isEmpty()) {
            log.warn("No suppliers found");
        }
        return fromEntityListToDTOList(suppliers);
    }

    public List<SupplierDTO> fromEntityListToDTOList(List<Supplier> suppliers) {
        log.info("Inside fromEntityListToDTOList method - Converting list of suppliers to list of supplier DTOs");

        List<SupplierDTO> supplierDTOs = new ArrayList<>();

        for (Supplier supplier : suppliers) {
            supplierDTOs.add(SupplierDTO.builder()
                    .id(supplier.getId())
                    .name(supplier.getName())
                    .build());
        }

        return supplierDTOs;
    }
}
