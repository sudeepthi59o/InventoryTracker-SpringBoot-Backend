package com.sr.inventory_tracker.controller;


import com.sr.inventory_tracker.DTO.CategoryDTO;
import com.sr.inventory_tracker.DTO.SupplierDTO;
import com.sr.inventory_tracker.error.CategoryNotFoundException;
import com.sr.inventory_tracker.error.ProductNotFoundException;
import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.DTO.ProductDTO;
import com.sr.inventory_tracker.DTO.ProductFilterDTO;
import com.sr.inventory_tracker.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new product", description = "Create a new product with the given details. The product must contain valid category and supplier information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation errors")
    })
    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) throws SupplierNotFoundException, CategoryNotFoundException {
        log.info("Inside createProduct method of ProductController - Creating new product: {}", productDTO);
        return productService.createProduct(productDTO);
    }


    @Operation(summary = "Get product by ID", description = "Retrieve a product by its ID. If no product is found with the given ID, a 404 error will be returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) throws ProductNotFoundException {
        log.info("Inside getProduct method of ProductController - Getting product with ID: {}", id);
        return productService.getProduct(id);
    }

    @Operation(summary = "Update a product", description = "Update the details of an existing product by its ID. The product data provided in the request body will replace the existing product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation errors"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) throws SupplierNotFoundException, CategoryNotFoundException, ProductNotFoundException {
        log.info("Inside updateProduct method of ProductController - Updating product with ID: {}", id);
        return productService.updateProduct(id, productDTO);
    }

    @Operation(summary = "Delete a product", description = "Delete a product by its ID. If the product is not found, a 404 error will be returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        log.info("Inside deleteProduct method of ProductController - Deleting product with ID: {}", id);
        productService.deleteProduct(id);
    }


    @Operation(summary = "Get all products", description = "Retrieve a list of all products in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all products", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        log.info("Inside getAllProducts method of ProductController - Getting all products");
        return productService.getAllProducts();
    }

    @Operation(
            summary = "Filter products by category and supplier", description = "Fetch a list of products filtered by category and/or supplier. Both category and supplier are optional parameters. If both are missing, all products will be returned."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
    })
    @GetMapping("/filter")
    public List<ProductDTO> getProductsByCategoryAndSupplier(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String supplier) throws SupplierNotFoundException, CategoryNotFoundException {

        ProductFilterDTO productFilterDTO = new ProductFilterDTO();

        if (category != null) {
            productFilterDTO.setCategoryDTO(CategoryDTO.builder().name(category).build());
        }

        if (supplier != null) {
            productFilterDTO.setSupplierDTO(SupplierDTO.builder().name(supplier).build());
        }

        return productService.getProductsByCategoryAndSupplier(productFilterDTO);
    }
}

