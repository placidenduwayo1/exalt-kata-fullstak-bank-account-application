package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos;

import lombok.*;

@Setter
@Getter
@Builder
public class BankAccountOverdraftDto {
    private String accountId;
    private double overdraft;
}
