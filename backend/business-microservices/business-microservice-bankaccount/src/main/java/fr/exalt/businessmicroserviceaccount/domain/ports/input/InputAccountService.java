package fr.exalt.businessmicroserviceaccount.domain.ports.input;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountDto;

import java.util.Collection;

public interface InputAccountService {
    BankAccount createAccount(BankAccountDto bankAccountDto) throws AccountTypeInvalidException, AccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException, AccountStateInvalidException;

    Collection<BankAccount> getAllAccounts();

    BankAccount getAccount(String accountId) throws AccountNotFoundException;

    Collection<BankAccount> getAccountOfGivenCustomer(String customerId);

    BankAccount updateAccount(String accountId, BankAccountDto bankAccountDto) throws AccountTypeInvalidException,
            AccountFieldsInvalidException, AccountNotFoundException, RemoteCustomerStateInvalidException,
            RemoteCustomerApiUnreachableException, AccountStateInvalidException, AccountTypeProvidedDifferentWithAccountTypeRegisteredException;

}
