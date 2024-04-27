package fr.exalt.businessmicroserviceaccount.domain.ports.input;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountSuspendDto;

import java.util.Collection;

public interface InputBankAccountService {
    BankAccount createAccount(BankAccountDto bankAccountDto) throws BankAccountTypeInvalidException, BankAccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException, BankAccountStateInvalidException;

    Collection<BankAccount> getAllAccounts();

    BankAccount getAccount(String accountId) throws BankAccountNotFoundException;

    Collection<BankAccount> getAccountOfGivenCustomer(String customerId);

    BankAccount updateAccount(String accountId, BankAccountDto bankAccountDto) throws BankAccountTypeInvalidException,
            BankAccountFieldsInvalidException, BankAccountNotFoundException, RemoteCustomerStateInvalidException,
            RemoteCustomerApiUnreachableException, BankAccountStateInvalidException,
            BankAccountTypeProvidedDifferentWithAccountTypeRegisteredException;
    BankAccount suspendAccount(BankAccountSuspendDto dto) throws BankAccountNotFoundException,
            BankAccountStateInvalidException, BankAccountAlreadySuspendException;

}
