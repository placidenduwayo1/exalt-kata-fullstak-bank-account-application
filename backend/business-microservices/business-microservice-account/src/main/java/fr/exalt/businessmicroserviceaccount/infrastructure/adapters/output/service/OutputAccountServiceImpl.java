package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceaccount.domain.entities.Account;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
@AllArgsConstructor
public class OutputAccountServiceImpl implements OutputAccountService {
    private final AccountRepository accountRepository;
    private final RemoteCustomerServiceProxy remoteCustomerService;
    @Override
    public Account createAccount(Account account) {
        AccountModel model= accountRepository.save(MapperService.fromTo(account));
        return MapperService.fromTo(model);
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

    @Override
    public Customer loadRemoteCustomer(String customerId) {
       return MapperService.fromTo(remoteCustomerService.loadRemoteCustomer(customerId));
    }
}
