package fr.exalt.businessmicroserviceoperation.domain.ports.output;

import fr.exalt.businessmicroserviceoperation.domain.entities.Account;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.AccountDto;

import java.util.Collection;

public interface OutputOperationService {
    Operation createOperation(Operation operation);
    Collection<Operation> getAllOperations();
    Account loadRemoteAccount(String accountId);
    Customer loaddRemoteCustomer(String customerId);
    Account updateRemoteAccount(String accountId, AccountDto accountDto);
}
