package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AddressDto {
    private int streetNum;
    private String streetName;
    private int poBox;
    private String city;
    private String country;
}
