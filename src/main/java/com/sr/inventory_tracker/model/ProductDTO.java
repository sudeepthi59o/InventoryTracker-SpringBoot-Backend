package com.sr.inventory_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

    private SupplierDTO supplierDTO;
    private CategoryDTO categoryDTO;
}
