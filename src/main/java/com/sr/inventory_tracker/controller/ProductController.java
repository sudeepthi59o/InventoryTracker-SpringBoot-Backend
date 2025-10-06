package com.sr.inventory_tracker.controller;


import com.sr.inventory_tracker.error.CategoryNotFoundException;
import com.sr.inventory_tracker.error.ProductNotFoundException;
import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.model.ProductDTO;
import com.sr.inventory_tracker.service.ProductService;
import com.sr.inventory_tracker.service.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) throws SupplierNotFoundException, CategoryNotFoundException {
        log.info("Inside createProduct method of ProductController - Creating new product: {}", productDTO);
        return productService.createProduct(productDTO);
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) throws ProductNotFoundException {
        log.info("Inside getProduct method of ProductController - Getting product with ID: {}", id);
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) throws SupplierNotFoundException, CategoryNotFoundException, ProductNotFoundException {
        log.info("Inside updateProduct method of ProductController - Updating product with ID: {}", id);
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        log.info("Inside deleteProduct method of ProductController - Deleting product with ID: {}", id);
        productService.deleteProduct(id);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        log.info("Inside getAllProducts method of ProductController - Getting all products");
        return productService.getAllProducts();
    }

}
