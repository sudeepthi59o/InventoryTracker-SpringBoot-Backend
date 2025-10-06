package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.model.SupplierDTO;

import java.util.List;

public interface SupplierService {

    SupplierDTO getSupplier(Long id) throws SupplierNotFoundException;

    List<SupplierDTO> getAllSuppliers();
}
