package com.sr.inventory_tracker.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Supplier {

    @Id
    @SequenceGenerator(name = "supplier_sequence", sequenceName = "supplier_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_sequence")
    private Long id;
    private String name;
    private String contact;
    private String email;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;


}
