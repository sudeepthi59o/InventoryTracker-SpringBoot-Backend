package com.sr.inventory_tracker.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AttributeOverrides({
        @AttributeOverride(name = "address_line1", column = @Column(name = "addressLine1")),
        @AttributeOverride(name = "address_line2", column = @Column(name = "addressLine2")),
        @AttributeOverride(name = "city", column = @Column(name = "city")),
        @AttributeOverride(name = "state", column = @Column(name = "state")),
        @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode"))
}
)
public class Address {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;

}
