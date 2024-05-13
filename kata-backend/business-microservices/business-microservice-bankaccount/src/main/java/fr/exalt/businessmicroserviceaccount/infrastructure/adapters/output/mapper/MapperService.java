package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.CurrentBankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.SavingBankAccountModel;
import org.springframework.beans.BeanUtils;

public class MapperService {
    private MapperService(){}
    public static CurrentBankAccount mapToCurrentAccount(BankAccountDto dto){
        CurrentBankAccount currentAccount = new CurrentBankAccount.CurrentAccountBuilder().build();
        BeanUtils.copyProperties(dto, currentAccount);
        return currentAccount;
    }
    public static CurrentBankAccount mapToCurrentAccount(CurrentBankAccountModel model){
        CurrentBankAccount currentAccount = new CurrentBankAccount.CurrentAccountBuilder().build();
        BeanUtils.copyProperties(model, currentAccount);
        return currentAccount;
    }
    public static CurrentBankAccountModel mapToCurrentAccountModel(CurrentBankAccount account){
        CurrentBankAccountModel model = new CurrentBankAccountModel();
        BeanUtils.copyProperties(account, model);
        return model;
    }
    public static SavingBankAccount mapToSavingAccount(BankAccountDto dto){
       SavingBankAccount savingAccount = new SavingBankAccount.SavingAccountBuilder().build();
       BeanUtils.copyProperties(dto, savingAccount);
       return savingAccount;
    }
    public static SavingBankAccount mapToSavingAccount(SavingBankAccountModel model) {
        SavingBankAccount account = new SavingBankAccount.SavingAccountBuilder().build();
        BeanUtils.copyProperties(model, account);
        return account;
    }
    public static SavingBankAccountModel mapToSavingAccountModel(SavingBankAccount account){
        SavingBankAccountModel model = new SavingBankAccountModel();
        BeanUtils.copyProperties(account, model);
        return model;
    }

    public static Customer fromTo(CustomerModel model){
        Customer customer = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(model, customer);
        return customer;
    }
}
