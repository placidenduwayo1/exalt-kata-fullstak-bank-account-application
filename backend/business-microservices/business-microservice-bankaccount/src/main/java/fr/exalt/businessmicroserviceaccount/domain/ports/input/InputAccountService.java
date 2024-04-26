package fr.exalt.businessmicroserviceaccount.domain.ports.input;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;

import java.util.Collection;

public interface InputAccountService {
    BankAccount createAccount(AccountDto accountDto) throws AccountTypeInvalidException, AccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException;

    Collection<BankAccount> getAllAccounts();

    BankAccount getAccount(String accountId) throws AccountNotFoundException;

    Collection<BankAccount> getAccountOfGivenCustomer(String customerId);

    BankAccount updateAccount(String accountId, AccountDto accountDto) throws AccountTypeInvalidException,
            AccountFieldsInvalidException, AccountNotFoundException, RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException;

}
