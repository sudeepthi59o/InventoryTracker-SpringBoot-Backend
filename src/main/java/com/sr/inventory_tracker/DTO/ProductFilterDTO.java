package com.sr.inventory_tracker.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDTO {

    private SupplierDTO supplierDTO;
    private CategoryDTO categoryDTO;
}
