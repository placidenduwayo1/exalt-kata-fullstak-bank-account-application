package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class BankAccountModel {
    private String accountId;
    private String type;
    private String state;
    private double balance;
    private double overdraft;
    private String customerId;
    private CustomerModel customerModel;
}
