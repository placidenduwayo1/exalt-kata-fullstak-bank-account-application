package fr.exalt.businessmicroserviceaccount.domain.ports.output;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountNotFoundException;

import java.util.Collection;

public interface OutputAccountService {
    CurrentBankAccount createCurrentAccount(CurrentBankAccount currentAccount);
    SavingBankAccount createSavingAccount(SavingBankAccount savingAccount);

    Collection<BankAccount> getAllAccounts();

    BankAccount getAccount(String accountId) throws AccountNotFoundException;

    Collection<BankAccount> getAccountOfGivenCustomer(String customerId);

    Customer loadRemoteCustomer(String customerId);

    CurrentBankAccount updateCurrentAccount(CurrentBankAccount currentAccount);
    SavingBankAccount updateSavingAccount(SavingBankAccount savingBankAccount);

}
