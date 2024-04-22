package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomerDto {
    private String firstname;
    private String lastname;
    private String state;
    private String addressId;
}
