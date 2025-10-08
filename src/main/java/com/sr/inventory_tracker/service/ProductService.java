package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.CategoryNotFoundException;
import com.sr.inventory_tracker.error.ProductNotFoundException;
import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.DTO.ProductDTO;
import com.sr.inventory_tracker.DTO.ProductFilterDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {

    ProductDTO getProduct(Long id) throws ProductNotFoundException;

    ProductDTO createProduct(ProductDTO productDTO) throws SupplierNotFoundException, CategoryNotFoundException;

    ProductDTO updateProduct(Long id, ProductDTO productDTO) throws ProductNotFoundException, SupplierNotFoundException, CategoryNotFoundException;

    void deleteProduct(Long id) throws ProductNotFoundException;

    List<ProductDTO> getAllProducts();

    List<ProductDTO> getProductsByCategoryAndSupplier(ProductFilterDTO productFilterDTO) throws SupplierNotFoundException, CategoryNotFoundException;
}
