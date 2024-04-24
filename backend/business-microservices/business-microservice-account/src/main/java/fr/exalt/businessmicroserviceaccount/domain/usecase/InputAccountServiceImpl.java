package fr.exalt.businessmicroserviceaccount.domain.usecase;

import fr.exalt.businessmicroserviceaccount.domain.entities.Account;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputAccountService;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class InputAccountServiceImpl implements InputAccountService {
    private final OutputAccountService outputAccountService;

    @Override
    public Account createAccount(AccountDto accountDto) throws AccountTypeInvalidException, AccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException {
        AccountValidators.formatter(accountDto);
        if (AccountValidators.validAccountType(accountDto.getType())) {
            throw new AccountTypeInvalidException(ExceptionMsg.ACCOUNT_TYPE_INVALID);
        }
        if (AccountValidators.invalidFields(accountDto)) {
            throw new AccountFieldsInvalidException(ExceptionMsg.ACCOUNT_FIELDS_INVALID);
        }
        Customer customer = outputAccountService.loadRemoteCustomer(accountDto.getCustomerId());
        if (customer.getCustomerId().equals("It is possible that remote customer is unreachable")) {
            throw new RemoteCustomerApiUnreachableException(ExceptionMsg.REMOTE_CUSTOMER_API);
        } else if (AccountValidators.remoteCustomerStateInvalid(customer.getState())) {
            throw new RemoteCustomerStateInvalidException(ExceptionMsg.REMOTE_CUSTOMER_STATE);
        }
        Account account = MapperService.fromTo(accountDto);
        account.setAccountId(UUID.randomUUID().toString());
        account.setCreatedAt(Timestamp.from(Instant.now()).toString());
        account.setCustomer(customer);

        return outputAccountService.createAccount(account);
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
