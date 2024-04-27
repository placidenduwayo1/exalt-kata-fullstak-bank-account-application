package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BankAccountDto {
    private String type;
    private double balance;
    private String customerId;
}
