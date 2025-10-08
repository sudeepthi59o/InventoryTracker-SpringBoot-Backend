package com.sr.inventory_tracker.repository;

import com.sr.inventory_tracker.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.supplier.id = ?1 and p.category.id = ?2")
    List<Product> findProductsByCategoryAndSupplier(Long supplierId, Long categoryId);

    List<Product> findBySupplierId(Long id);

    List<Product> findByCategoryId(Long id);
}
