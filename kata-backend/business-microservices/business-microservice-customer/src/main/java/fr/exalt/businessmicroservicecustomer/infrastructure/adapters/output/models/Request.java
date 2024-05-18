package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private Customer customer;
    private Address address;
}
