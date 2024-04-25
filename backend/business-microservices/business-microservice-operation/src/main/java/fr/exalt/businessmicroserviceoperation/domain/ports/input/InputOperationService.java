package fr.exalt.businessmicroserviceoperation.domain.ports.input;

import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;

import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationRequestDto;

public interface InputOperationService {
    Operation createOperation(OperationRequestDto operationRequestDto);

}
