package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountNotFoundException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.ExceptionMsg;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.CurrentBankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.SavingBankAccountModel;
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
    public CurrentBankAccount createCurrentAccount(CurrentBankAccount currentAccount) {
        CurrentBankAccountModel model= accountRepository.save(MapperService.mapToCurrentAccountModel(currentAccount));
        return MapperService.mapToCurrentAccount(model);
    }


    @Override
    public SavingBankAccount createSavingAccount(SavingBankAccount savingAccount) {
        SavingBankAccountModel model = accountRepository.save(MapperService.mapToSavingAccountModel(savingAccount));
        return MapperService.mapToSavingAccount(model);
    }


    @Override
    public Collection<BankAccount> getAllAccounts() {
        return mapBankAccounts(accountRepository.findAll());
    }

    @Override
    public BankAccount getAccount(String accountId) throws AccountNotFoundException {
        BankAccountModel model = accountRepository.findById(accountId)
                .orElseThrow(()->new AccountNotFoundException(ExceptionMsg.ACCOUNT_NOT_FOUND));
        return mapBankAccount(model);
    }

    @Override
    public Collection<BankAccount> getAccountOfGivenCustomer(String customerId) {
        return mapBankAccounts(accountRepository.findByCustomerId(customerId));
    }


    @Override
    public Customer loadRemoteCustomer(String customerId) {
        CustomerModel model = remoteCustomerService.loadRemoteCustomer(customerId);
        return MapperService.fromTo(model);
    }

    @Override
    public CurrentBankAccount updateCurrentAccount(CurrentBankAccount currentAccount) {
        CurrentBankAccountModel updateModel = accountRepository.save(MapperService.mapToCurrentAccountModel(currentAccount));
        return MapperService.mapToCurrentAccount(updateModel);
    }

    @Override
    public SavingBankAccount updateSavingAccount(SavingBankAccount savingBankAccount) {
        SavingBankAccountModel model = accountRepository.save(MapperService.mapToSavingAccountModel(savingBankAccount));
        return MapperService.mapToSavingAccount(model);
    }

    private Collection<BankAccount> mapBankAccounts(Collection<BankAccountModel> bankAccountModels){
       return bankAccountModels.stream().map(model -> {
           BankAccount bankAccount = null;
           if(model instanceof CurrentBankAccountModel current){
               bankAccount = MapperService.mapToCurrentAccount(current);
               bankAccount.setType("compte-courant");
           }
           else if(model instanceof SavingBankAccountModel saving){
               bankAccount = MapperService.mapToSavingAccount(saving);
               bankAccount.setType("compte-epargne");
           }
           return bankAccount;
        }).toList();
    }

    private BankAccount mapBankAccount(BankAccountModel model){
        if(model instanceof CurrentBankAccountModel current){
            return MapperService.mapToCurrentAccount(current);
        }
        else if(model instanceof  SavingBankAccountModel saving){
            return MapperService.mapToSavingAccount(saving);
        }
        return null;
    }
}
