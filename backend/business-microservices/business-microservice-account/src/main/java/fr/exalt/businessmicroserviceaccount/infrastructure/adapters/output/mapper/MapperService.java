package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroserviceaccount.domain.entities.Account;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountModel;
import org.springframework.beans.BeanUtils;

public class MapperService {
    private MapperService(){}
    public static Account fromTo(AccountDto dto){
        Account account = new Account.AccountBuilder().build();
        BeanUtils.copyProperties(dto,account);
        return account;
    }
    public static Account fromTo(AccountModel model){
        Account account = new Account.AccountBuilder().build();
        BeanUtils.copyProperties(model, account);
        return account;
    }
    public static Customer fromTo(CustomerModel model){
        Customer customer = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(model, customer);
        return customer;
    }
    public static AccountModel fromTo(Account account){
        AccountModel model = AccountModel.builder().build();
        BeanUtils.copyProperties(account, model);
        return model;
    }
    public static CustomerModel fromTo(Customer customer){
        CustomerModel model = CustomerModel.builder().build();
        BeanUtils.copyProperties(customer, model);
        return model;
    }
}
