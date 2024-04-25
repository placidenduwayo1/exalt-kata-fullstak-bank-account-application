package fr.exalt.businessmicroserviceoperation.domain.ports.input;

import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;

import fr.exalt.businessmicroserviceoperation.domain.exceptions.OperationRequestFieldsInvalidException;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.OperationTypeInvalidException;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.RemoteAccountNotEnoughBalanceException;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationDto;

import java.util.Collection;

public interface InputOperationService {
    Operation createOperation(OperationDto dto) throws OperationRequestFieldsInvalidException,
            OperationTypeInvalidException, RemoteAccountApiUnreachableException, RemoteAccountNotEnoughBalanceException;
    Collection<Operation> getAllOperations();
}
