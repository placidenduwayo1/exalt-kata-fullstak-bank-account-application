package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient;

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
    private String type;
    private double balance;
    private double overdraft;
}
