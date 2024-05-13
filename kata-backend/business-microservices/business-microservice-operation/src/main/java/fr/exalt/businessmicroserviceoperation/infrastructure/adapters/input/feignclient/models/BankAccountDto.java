package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class BankAccountDto {
    private String type;
    private String state;
    private double balance;
    private double overdraft;
    private String customerId;
}
