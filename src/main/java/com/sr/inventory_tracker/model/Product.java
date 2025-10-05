package com.sr.inventory_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;


    @ManyToOne(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(
            name = "supplier_id",
            referencedColumnName = "id"
    )
    private Supplier supplier;


    @ManyToOne(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
    )
    private Category category;
}
