package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroserviceoperation.domain.entities.Account;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.AccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.AccountModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationModel;
import org.springframework.beans.BeanUtils;

public class MapperService {
    private MapperService() {
    }

    public static Operation fromTo(OperationDto dto) {
        Operation operation = new Operation.OperationBuilder().build();
        BeanUtils.copyProperties(dto, operation);
        return operation;
    }

    public static Operation fromTo(OperationModel model) {
        Operation operation = new Operation.OperationBuilder().build();
        BeanUtils.copyProperties(model, operation);
        return operation;
    }

    public static OperationModel fromTo(Operation operation){
        OperationModel model = OperationModel.builder().build();
        BeanUtils.copyProperties(operation, model);
        return model;
    }

    public static Account fromTo(AccountModel model){
        Account account = new Account.AccountBuilder().build();
        BeanUtils.copyProperties(model, account);
        return account;
    }
    public static AccountDto fromTo(Account account){
        AccountDto accountDto = AccountDto.builder().build();
        BeanUtils.copyProperties(account, accountDto);
        return accountDto;
    }
    public static Customer fromTo(CustomerModel model){
        Customer customer = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(model, customer);
        return customer;
    }
}
