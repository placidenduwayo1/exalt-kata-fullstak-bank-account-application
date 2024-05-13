package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountModel;
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

    public static BankAccount fromTo(BankAccountModel model){
        BankAccount bankAccount = new BankAccount.AccountBuilder().build();
        BeanUtils.copyProperties(model, bankAccount);
        return bankAccount;
    }
    public static BankAccountDto fromTo(BankAccount bankAccount){
        BankAccountDto bankAccountDto = BankAccountDto.builder().build();
        BeanUtils.copyProperties(bankAccount, bankAccountDto);
        return bankAccountDto;
    }
    public static Customer fromTo(CustomerModel model){
        Customer customer = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(model, customer);
        return customer;
    }
}
