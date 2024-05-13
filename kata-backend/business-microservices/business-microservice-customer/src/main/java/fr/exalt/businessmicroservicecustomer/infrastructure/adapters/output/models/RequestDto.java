package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class RequestDto {
    private CustomerDto customerDto;
    private AddressDto addressDto;
}
