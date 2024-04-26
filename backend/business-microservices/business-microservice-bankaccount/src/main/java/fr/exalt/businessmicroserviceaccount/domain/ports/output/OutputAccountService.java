package fr.exalt.businessmicroserviceaccount.domain.ports.output;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountNotFoundException;

import java.util.Collection;

public interface OutputAccountService {
    BankAccount createAccount(BankAccount bankAccount);

    Collection<BankAccount> getAllAccounts();

    BankAccount getAccount(String accountId) throws AccountNotFoundException;

    Collection<BankAccount> getAccountOfGivenCustomer(String customerId);

    Customer loadRemoteCustomer(String customerId);

    BankAccount updateAccount(BankAccount bankAccount);
}
