package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.CategoryNotFoundException;
import com.sr.inventory_tracker.error.ProductNotFoundException;
import com.sr.inventory_tracker.error.SupplierNotFoundException;
import com.sr.inventory_tracker.model.*;
import com.sr.inventory_tracker.repository.CategoryRepository;
import com.sr.inventory_tracker.repository.ProductRepository;
import com.sr.inventory_tracker.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;
    private Supplier supplier;
    private Category category;

    @BeforeEach
    public void setUp() {
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Supplier1");

        category = new Category();
        category.setId(1L);
        category.setName("Category1");

        product = new Product();
        product.setId(1L);
        product.setName("Product1");
        product.setPrice(100.0);
        product.setQuantity(10);
        product.setSupplier(supplier);
        product.setCategory(category);

        productDTO = new ProductDTO();
        productDTO.setName("Product1");
        productDTO.setPrice(100.0);
        productDTO.setQuantity(10);
        productDTO.setSupplierDTO(new SupplierDTO(1L, "Supplier1"));
        productDTO.setCategoryDTO(new CategoryDTO(1L, "Category1"));
    }

    @Test
    public void testGetProduct_Success() throws ProductNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProduct(1L);

        assertNotNull(result);
        assertEquals("Product1", result.getName());
        assertEquals(100.0, result.getPrice());
    }

    @Test
    public void testGetProduct_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(1L));
    }

    @Test
    public void testCreateProduct_Success() throws SupplierNotFoundException, CategoryNotFoundException {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertEquals("Product1", result.getName());
        assertEquals(100.0, result.getPrice());
    }

    @Test
    public void testCreateProduct_SupplierNotFound() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> productService.createProduct(productDTO));
    }

    @Test
    public void testCreateProduct_CategoryNotFound() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.createProduct(productDTO));
    }

    @Test
    public void testUpdateProduct_Success() throws ProductNotFoundException, SupplierNotFoundException, CategoryNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setName("Updated Product");
        updatedProductDTO.setPrice(120.0);
        updatedProductDTO.setQuantity(15);
        updatedProductDTO.setSupplierDTO(new SupplierDTO(1L, "Supplier1"));
        updatedProductDTO.setCategoryDTO(new CategoryDTO(1L, "Category1"));

        ProductDTO result = productService.updateProduct(1L, updatedProductDTO);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(120.0, result.getPrice());
    }

    @Test
    public void testUpdateProduct_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, productDTO));
    }

    @Test
    public void testUpdateProduct_SupplierNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> productService.updateProduct(1L, productDTO));
    }

    @Test
    public void testUpdateProduct_CategoryNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.updateProduct(1L, productDTO));
    }

    @Test
    public void testDeleteProduct_Success() throws ProductNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }

    @Test
    public void testDeleteProduct_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
    }

    @Test
    public void testGetAllProducts_Success() {
        List<Product> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getName());
    }

    @Test
    public void testGetAllProducts_Empty() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(0, result.size());
    }


}