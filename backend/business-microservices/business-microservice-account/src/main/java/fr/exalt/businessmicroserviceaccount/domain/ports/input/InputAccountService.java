package fr.exalt.businessmicroserviceaccount.domain.ports.input;

import fr.exalt.businessmicroserviceaccount.domain.entities.Account;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;

import java.util.Collection;

public interface InputAccountService {
    Account createAccount(AccountDto accountDto) throws AccountTypeInvalidException, AccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException;
    Collection<Account> getAllAccounts();
    Account getAccount(String accountId) throws AccountNotFoundException;
    Collection<Account> getAccountOfGivenCustomer(String customerId);
}
