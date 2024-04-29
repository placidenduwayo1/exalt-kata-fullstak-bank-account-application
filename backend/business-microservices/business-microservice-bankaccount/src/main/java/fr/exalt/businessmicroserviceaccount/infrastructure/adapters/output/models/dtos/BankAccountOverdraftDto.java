package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountOverdraftDto {
    private String accountId;
    private double overdraft;
}