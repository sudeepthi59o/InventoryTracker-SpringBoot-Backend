package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.CategoryNotFoundException;
import com.sr.inventory_tracker.error.ProductNotFoundException;
import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.model.*;
import com.sr.inventory_tracker.repository.CategoryRepository;
import com.sr.inventory_tracker.repository.ProductRepository;
import com.sr.inventory_tracker.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, SupplierRepository supplierRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO getProduct(Long id) throws ProductNotFoundException {
        log.info("Inside getProduct method - Fetching product with ID: {}", id);

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            log.error("Product with ID {} not found", id);
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }

        ProductDTO productDTO = convertToDTO(product.get());

        log.info("Successfully fetched product with ID: {}", id);
        return productDTO;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) throws SupplierNotFoundException, CategoryNotFoundException {

        Supplier supplier = getSupplierOrThrow(productDTO.getSupplierDTO().getId());
        Category category = getCategoryOrThrow(productDTO.getCategoryDTO().getId());

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        product.setSupplier(supplier);
        product.setCategory(category);

        product = productRepository.save(product);

        log.info("Product with ID {} created", product.getId());
        return convertToDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) throws ProductNotFoundException, SupplierNotFoundException, CategoryNotFoundException {
        log.info("Inside updateProduct method - Updating product with ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        Supplier supplier = getSupplierOrThrow(productDTO.getSupplierDTO().getId());
        Category category = getCategoryOrThrow(productDTO.getCategoryDTO().getId());

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        product.setSupplier(supplier);
        product.setCategory(category);

        product = productRepository.save(product);
        log.info("Product with ID {} updated", product.getId());

        return convertToDTO(product);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        log.info("Inside deleteProduct method - Deleting product with ID: {}", id);

        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            log.error("Product with ID {} not found for deletion", id);
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }

        productRepository.delete(product.get());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        log.info("Inside getAllProducts method - Fetching all products");

        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            log.warn("No products found");
        }

        return fromEntityListToDTOList(products);
    }

    public List<ProductDTO> fromEntityListToDTOList(List<Product> products) {
        log.info("Inside fromEntityListToDTOList method - Converting list of products to list of product DTOs");

        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            productDTOs.add(convertToDTO(product));
        }

        return productDTOs;
    }

    private ProductDTO convertToDTO(Product product) {
        log.info("Converting Product to ProductDTO - ID: {}", product.getId());

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .supplierDTO(new SupplierDTO(product.getSupplier().getId(), product.getSupplier().getName()))
                .categoryDTO(new CategoryDTO(product.getCategory().getId(), product.getCategory().getName()))
                .build();
    }

    private Supplier getSupplierOrThrow(Long supplierId) throws SupplierNotFoundException {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with ID " + supplierId + " not found"));
    }

    private Category getCategoryOrThrow(Long categoryId) throws CategoryNotFoundException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + categoryId + " not found"));
    }


}
