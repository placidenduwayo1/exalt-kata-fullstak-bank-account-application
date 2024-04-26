package fr.exalt.businessmicroserviceaccount.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SavingBankAccount extends BankAccount{
    private double interestRate;
}
