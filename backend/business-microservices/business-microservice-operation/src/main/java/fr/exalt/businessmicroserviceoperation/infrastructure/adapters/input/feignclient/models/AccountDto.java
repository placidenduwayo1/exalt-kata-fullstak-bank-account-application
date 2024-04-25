package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class AccountDto {
    private String type;
    private double balance;
    private double overdraft;
    private String customerId;
}
