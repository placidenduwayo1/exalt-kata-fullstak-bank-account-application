package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private CustomerDto customerDto;
    private AddressDto addressDto;
}
