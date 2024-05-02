package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class CustomerDto {
    private String firstname;
    private String lastname;
    private String state;
    private String email;
}
