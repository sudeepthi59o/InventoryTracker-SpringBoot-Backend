package com.sr.inventory_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products", uniqueConstraints = @UniqueConstraint(name = "unique_name", columnNames = "name"))
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long id;

    @NotNull(message = "Product name cannot be null")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be at least 0")
    private Integer quantity;


    @ManyToOne(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(
            name = "supplier_id",
            referencedColumnName = "id"
    )
    @NotNull(message = "Supplier cannot be null")
    private Supplier supplier;


    @ManyToOne(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
    )
    @NotNull(message = "Category cannot be null")
    private Category category;
}
