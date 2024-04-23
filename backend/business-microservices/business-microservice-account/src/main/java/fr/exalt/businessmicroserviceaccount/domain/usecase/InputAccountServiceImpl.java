package fr.exalt.businessmicroserviceaccount.domain.usecase;

import fr.exalt.businessmicroserviceaccount.domain.entities.Account;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountBalanceInvalidException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountFieldsInvalidException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountTypeInvalidException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.ExceptionMsg;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;

import java.util.Collection;

public class InputAccountServiceImpl implements InputAccountService {
    @Override
    public Account createAccount(AccountDto accountDto) throws AccountTypeInvalidException, AccountFieldsInvalidException {
       AccountValidators.formatter(accountDto);
        if(AccountValidators.validAccountType(accountDto.getType())){
           throw new AccountTypeInvalidException(ExceptionMsg.ACCOUNT_TYPE_INVALID);
        }
       if(AccountValidators.invalidFields(accountDto)){
           throw new AccountFieldsInvalidException(ExceptionMsg.ACCOUNT_FIELDS_INVALID);
       }

       return null;
    }

    @Override
    public Collection<Account> getAllAccounts() {
        return null;
    }

    @Override
    public Account getAccount(String accountId) {
        return null;
    }

    @Override
    public Account getAccount(AccountDto dto) {
        return null;
    }
}
