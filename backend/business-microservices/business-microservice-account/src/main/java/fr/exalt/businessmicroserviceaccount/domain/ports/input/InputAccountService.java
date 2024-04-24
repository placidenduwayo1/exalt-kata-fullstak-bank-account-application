package fr.exalt.businessmicroserviceaccount.domain.ports.input;

import fr.exalt.businessmicroserviceaccount.domain.entities.Account;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountFieldsInvalidException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountTypeInvalidException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.RemoteCustomerApiUnreachableException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.RemoteCustomerStateInvalidException;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;

import java.util.Collection;

public interface InputAccountService {
    Account createAccount(AccountDto accountDto) throws AccountTypeInvalidException, AccountFieldsInvalidException, RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException;
    Collection<Account> getAllAccounts();
    Account getAccount(String accountId);
    Account getAccount(AccountDto dto);
}
