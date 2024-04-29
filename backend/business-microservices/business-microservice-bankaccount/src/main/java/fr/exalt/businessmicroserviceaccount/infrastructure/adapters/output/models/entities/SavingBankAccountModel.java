package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@DiscriminatorValue("saving")
public class SavingBankAccountModel extends BankAccountModel{
    private double interestRate;
}
