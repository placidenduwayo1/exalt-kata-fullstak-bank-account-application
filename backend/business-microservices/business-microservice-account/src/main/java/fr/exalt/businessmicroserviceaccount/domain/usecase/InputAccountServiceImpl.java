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
    //output adapter
    private final OutputAccountService outputAccountService;

    @Override
    public Account createAccount(AccountDto accountDto) throws AccountTypeInvalidException, AccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException {
        AccountValidators.formatter(accountDto);
        if (!AccountValidators.validAccountType(accountDto.getType())) {
            throw new AccountTypeInvalidException(ExceptionMsg.ACCOUNT_TYPE_INVALID);
        }
        if (AccountValidators.invalidFields(accountDto)) {
            throw new AccountFieldsInvalidException(ExceptionMsg.ACCOUNT_FIELDS_INVALID);
        }
        Customer customer = outputAccountService.loadRemoteCustomer(accountDto.getCustomerId());

        if (AccountValidators.remoteCustomerApiUnreachable(customer.getCustomerId())) {
            throw new RemoteCustomerApiUnreachableException(String.format("%s,%n%s", ExceptionMsg.REMOTE_CUSTOMER_API, customer));
        } else if (AccountValidators.remoteCustomerStateInvalid(customer.getState())) {
            throw new RemoteCustomerStateInvalidException(String.format("%s,%n%s", ExceptionMsg.REMOTE_CUSTOMER_STATE, customer));
        }
        Account account = MapperService.fromTo(accountDto);
        account.setAccountId(UUID.randomUUID().toString());
        account.setCreatedAt(Timestamp.from(Instant.now()).toString());
        if(account.getType().equals("compte-epargne")){
            account.setOverdraft(0);//un compte epargne ne possede pas de decouvert
        }
        // passer le bean créé à l'adaptateur pour l'enregistrer en db
        Account saveAccount = outputAccountService.createAccount(account);
        saveAccount.setCustomer(customer);
        return saveAccount;
    }

    @Override
    public Collection<Account> getAllAccounts() {
        Collection<Account>  accounts = outputAccountService.getAllAccounts();
        return setCustomerToAccount(accounts);
    }

    @Override
    public Account getAccount(String accountId) throws AccountNotFoundException {
        Account account = outputAccountService.getAccount(accountId);
        Customer customer = outputAccountService.loadRemoteCustomer(account.getCustomerId());
        account.setCustomer(customer);
        return account;
    }

    @Override
    public Collection<Account> getAccountOfGivenCustomer(String customerId) {
        Collection<Account> accounts = outputAccountService.getAccountOfGivenCustomer(customerId);
        return setCustomerToAccount(accounts);
    }

    private Collection<Account> setCustomerToAccount(Collection<Account> accounts) {
        return accounts.stream()
                .map(account -> {
                    Customer remoteCustomer = outputAccountService.loadRemoteCustomer(account.getCustomerId());
                    account.setCustomer(remoteCustomer);
                    return account;
                })
                .toList();
    }
}
