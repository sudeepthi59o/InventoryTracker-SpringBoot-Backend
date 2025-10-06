package com.sr.inventory_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "suppliers")
public class Supplier {

    @Id
    @SequenceGenerator(name = "supplier_sequence", sequenceName = "supplier_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_sequence")
    private Long id;

    @NotNull(message = "Supplier name cannot be null")
    @Size(min = 3, max = 100, message = "Supplier name must be between 3 and 100 characters")
    private String name;

    // Contact number can be optional but must match a valid phone number pattern if provided
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid contact number format")
    private String contact;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @Embedded
    @Valid  // Address validation triggered
    private Address address;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;


}
