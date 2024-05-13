package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos;

import lombok.*;

@Setter
@Getter
@Builder
public class BankAccountInterestRateDto {
    private String accountId;
    private double interestRate;
}
