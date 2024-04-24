package fr.exalt.businessmicroserviceaccount.domain.ports.output;

import fr.exalt.businessmicroserviceaccount.domain.entities.Account;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountNotFoundException;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;

import java.util.Collection;

public interface OutputAccountService {
    Account createAccount(Account account);
    Collection<Account> getAllAccounts();
    Account getAccount(String accountId) throws AccountNotFoundException;
    Customer loadRemoteCustomer(String customerId);
}
