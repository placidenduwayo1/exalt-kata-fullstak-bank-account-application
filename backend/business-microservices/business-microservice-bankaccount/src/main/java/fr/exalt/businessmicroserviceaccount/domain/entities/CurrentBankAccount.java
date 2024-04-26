package fr.exalt.businessmicroserviceaccount.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentBankAccount extends BankAccount{
    private double overdraft;
}
