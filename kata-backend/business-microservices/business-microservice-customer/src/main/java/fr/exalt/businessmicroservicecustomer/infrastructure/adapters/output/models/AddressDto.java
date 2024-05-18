package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private int streetNum;
    private String streetName;
    private int poBox;
    private String city;
    private String country;
}
