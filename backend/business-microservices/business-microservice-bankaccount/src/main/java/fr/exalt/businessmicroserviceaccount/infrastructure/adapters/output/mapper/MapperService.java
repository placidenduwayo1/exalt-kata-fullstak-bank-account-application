package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountModel;
import org.springframework.beans.BeanUtils;

public class MapperService {
    private MapperService(){}
    public static BankAccount fromTo(BankAccountDto dto){
        BankAccount bankAccount = new BankAccount.AccountBuilder().build();
        BeanUtils.copyProperties(dto, bankAccount);
        return bankAccount;
    }
    public static BankAccount fromTo(BankAccountModel model){
        BankAccount bankAccount = new BankAccount.AccountBuilder().build();
        BeanUtils.copyProperties(model, bankAccount);
        return bankAccount;
    }
    public static Customer fromTo(CustomerModel model){
        Customer customer = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(model, customer);
        return customer;
    }
    public static BankAccountModel fromTo(BankAccount bankAccount){
        BankAccountModel model = BankAccountModel.builder().build();
        BeanUtils.copyProperties(bankAccount, model);
        return model;
    }
    public static CustomerModel fromTo(Customer customer){
        CustomerModel model = CustomerModel.builder().build();
        BeanUtils.copyProperties(customer, model);
        return model;
    }
}
