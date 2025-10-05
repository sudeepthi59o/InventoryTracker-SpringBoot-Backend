package com.sr.inventory_tracker.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "address_line1", column = @Column(name = "addressLine1")),
        @AttributeOverride(name = "address_line2", column = @Column(name = "addressLine2")),
        @AttributeOverride(name = "city", column = @Column(name = "city")),
        @AttributeOverride(name = "state", column = @Column(name = "state")),
        @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode"))
}
)
public class Address {

    @NotNull(message = "Address Line 1 cannot be null")
    @Size(min = 3, max = 255, message = "Address Line 1 must be between 3 and 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "Address Line 2 must be less than or equal to 255 characters")
    private String addressLine2;

    @NotNull(message = "City cannot be null")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
    private String city;

    @NotNull(message = "State cannot be null")
    @Size(min = 2, max = 100, message = "State name must be between 2 and 100 characters")
    private String state;

    @NotNull(message = "Zipcode cannot be null")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Zipcode must be in the format 12345 or 12345-6789")
    private String zipcode;

}
