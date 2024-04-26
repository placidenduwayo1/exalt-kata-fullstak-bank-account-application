package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroservicecustomer.domain.entities.BankAccount;
import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input.feignclient.model.AccountModel;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressModel;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerModel;
import org.springframework.beans.BeanUtils;

public class MapperService {
    private MapperService(){}
    public static Customer fromTo(CustomerDto dto){
        Customer customer = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(dto,customer);
        return customer;
    }
    public static Address fromTo(AddressDto dto) {
        Address address = new Address.AddressBuilder().build();
        BeanUtils.copyProperties(dto, address);
        return address;
    }
    public static CustomerModel fromTo(Customer customer) {
        CustomerModel model = CustomerModel.builder().build();
        BeanUtils.copyProperties(customer, model);
        return model;
    }
    public static Customer fromTo(CustomerModel model) {
        Customer customer = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(model, customer);
        return customer;
    }
    public static AddressModel fromTo(Address address) {
        AddressModel model = AddressModel.builder().build();
        BeanUtils.copyProperties(address, model);
        return model;
    }
    public static Address fromTo(AddressModel model){
        Address address = new Address.AddressBuilder().build();
        BeanUtils.copyProperties(model,address);
        return address;
    }
    public static BankAccount fromTo(AccountModel model){
        BankAccount bankAccount = new BankAccount.AccountBuilder().build();
        BeanUtils.copyProperties(model, bankAccount);
        return bankAccount;
    }
}
