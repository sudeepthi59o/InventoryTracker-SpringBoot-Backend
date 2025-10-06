package com.sr.inventory_tracker.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDTO {

    private Long id;

    @NotNull(message = "Supplier name cannot be null")
    @Size(min = 3, max = 100, message = "Supplier name must be between 3 and 100 characters")
    private String name;
}
