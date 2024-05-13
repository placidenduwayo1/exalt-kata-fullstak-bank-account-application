package fr.exalt.businessmicroserviceoperation.domain.ports.output;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;

import java.util.Collection;

public interface OutputOperationService {
    Operation createOperation(Operation operation);
    Collection<Operation> getAllOperations();
    BankAccount loadRemoteAccount(String accountId);
    Customer loadRemoteCustomer(String customerId);
    BankAccount updateRemoteAccount(String accountId, BankAccountDto bankAccountDto);

    Collection<Operation> getAccountOperations(String accountId);
}
