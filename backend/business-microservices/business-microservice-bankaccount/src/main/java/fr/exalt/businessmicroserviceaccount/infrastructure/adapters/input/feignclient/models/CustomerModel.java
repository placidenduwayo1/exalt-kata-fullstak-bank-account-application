package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class CustomerModel {
    private String customerId;
    private String firstname;
    private String lastname;
    private String email;
    private String state;
}
