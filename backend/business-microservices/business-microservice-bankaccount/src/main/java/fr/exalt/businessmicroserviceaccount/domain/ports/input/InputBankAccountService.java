package fr.exalt.businessmicroserviceaccount.domain.ports.input;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountInterestRateDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountOverdraftDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountSwitchStatedDto;

import java.util.Collection;

public interface InputBankAccountService {
    BankAccount createAccount(BankAccountDto bankAccountDto) throws BankAccountTypeInvalidException, BankAccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException, BankAccountStateInvalidException;

    Collection<BankAccount> getAllAccounts();

    BankAccount getAccount(String accountId) throws BankAccountNotFoundException;

    Collection<BankAccount> getAccountOfGivenCustomer(String customerId);

    BankAccount updateAccount(String accountId, BankAccountDto bankAccountDto) throws BankAccountTypeInvalidException,
            BankAccountFieldsInvalidException, BankAccountNotFoundException, RemoteCustomerStateInvalidException,
            RemoteCustomerApiUnreachableException, BankAccountStateInvalidException;
    BankAccount switchAccountState(BankAccountSwitchStatedDto dto) throws BankAccountNotFoundException,
            BankAccountStateInvalidException, BankAccountSameStateException, RemoteCustomerApiUnreachableException,
            RemoteCustomerStateInvalidException;

    CurrentBankAccount changeOverdraft(BankAccountOverdraftDto dto) throws BankAccountNotFoundException,
            BankAccountOverdraftInvalidException, RemoteCustomerStateInvalidException, BankAccountSuspendException,
            BankAccountTypeNotAcceptedException, RemoteCustomerApiUnreachableException;

    SavingBankAccount changeInterestRate(BankAccountInterestRateDto dto) throws BankAccountNotFoundException, BankAccountSuspendException,
            RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException, BankAccountTypeNotAcceptedException;
}

