package com.sr.inventory_tracker.DTO;

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
public class CategoryDTO {

    private Long id;

    @NotNull(message = "Category name cannot be null")
    @Size(min = 3, max = 100, message = "Category name must be between 3 and 100 characters")
    private String name;
}
