package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AccountDto {
    private String type;
    private double balance;
    private double overdraft;
    private String customerId;
}
