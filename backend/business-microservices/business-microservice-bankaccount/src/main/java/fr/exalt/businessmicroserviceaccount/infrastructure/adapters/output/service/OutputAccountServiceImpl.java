package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountNotFoundException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.ExceptionMsg;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountModel;
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
    public BankAccount createAccount(BankAccount bankAccount) {
        BankAccountModel model= accountRepository.save(MapperService.fromTo(bankAccount));
        return MapperService.fromTo(model);
    }

    @Override
    public Collection<BankAccount> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(MapperService::fromTo)
                .toList();
    }

    @Override
    public BankAccount getAccount(String accountId) throws AccountNotFoundException {
        BankAccountModel model = accountRepository.findById(accountId)
                .orElseThrow(()->new AccountNotFoundException(ExceptionMsg.ACCOUNT_NOT_FOUND));
        return MapperService.fromTo(model);
    }

    @Override
    public Collection<BankAccount> getAccountOfGivenCustomer(String customerId) {
        return accountRepository.findByCustomerId(customerId).stream()
                .map(MapperService::fromTo)
                .toList();
    }


    @Override
    public Customer loadRemoteCustomer(String customerId) {
       return MapperService.fromTo(remoteCustomerService.loadRemoteCustomer(customerId));
    }

    @Override
    public BankAccount updateAccount(BankAccount bankAccount) {
        BankAccountModel modelModel = accountRepository.save(MapperService.fromTo(bankAccount));
        return MapperService.fromTo(modelModel);
    }
}
