package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceaccount.domain.entities.Account;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountNotFoundException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.ExceptionMsg;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Collection;
@Service
public class OutputAccountServiceImpl implements OutputAccountService {
    private final AccountRepository accountRepository;
    private final  RemoteCustomerServiceProxy remoteCustomerService;

    public OutputAccountServiceImpl(
            AccountRepository accountRepository,
            @Qualifier(value = "remoteCustomerService") RemoteCustomerServiceProxy remoteCustomerService) {
        this.accountRepository = accountRepository;
        this.remoteCustomerService = remoteCustomerService;
    }

    @Override
    public Account createAccount(Account account) {
        AccountModel model= accountRepository.save(MapperService.fromTo(account));
        return MapperService.fromTo(model);
    }

    @Override
    public Collection<Account> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(MapperService::fromTo)
                .toList();
    }

    @Override
    public Account getAccount(String accountId) throws AccountNotFoundException {
        AccountModel model = accountRepository.findById(accountId)
                .orElseThrow(()->new AccountNotFoundException(ExceptionMsg.ACCOUNT_NOT_FOUND));
        return MapperService.fromTo(model);
    }


    @Override
    public Customer loadRemoteCustomer(String customerId) {
       return MapperService.fromTo(remoteCustomerService.loadRemoteCustomer(customerId));
    }
}
