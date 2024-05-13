package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class CustomerModel {
    private String customerId;
    private String firstname;
    private String lastname;
    private String state;
    private String email;
}