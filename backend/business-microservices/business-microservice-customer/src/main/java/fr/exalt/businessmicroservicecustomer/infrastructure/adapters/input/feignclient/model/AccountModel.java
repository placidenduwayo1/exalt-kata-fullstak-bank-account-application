package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input.feignclient.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class AccountModel {
    private String accountId;
    private double balance;
    private double overdraft;
    private String createdAt;
}
