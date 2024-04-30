package fr.exalt.businessmicroserviceoperation.domain.ports.input;

import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;

import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationDto;

import java.util.Collection;

public interface InputOperationService {
    Operation createOperation(OperationDto dto) throws OperationRequestFieldsInvalidException,
            OperationTypeInvalidException, RemoteBankAccountApiUnreachableException,
            RemoteBankAccountBalanceException, RemoteBankAccountTypeInaccessibleFromOutsideException,
            RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException;

    Collection<Operation> getAllOperations();

    Collection<Operation> getAccountOperations(String accountId) throws RemoteBankAccountApiUnreachableException, RemoteBankAccountTypeInaccessibleFromOutsideException;
}
