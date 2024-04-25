package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.domain.ports.input.InputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api-operation")
@AllArgsConstructor
public class OperationController {
    // adapter inputOperationService comme interface entre le domain et l'entrée extérieure
    private final InputOperationService inputOperationService;

    @PostMapping(value = "/operations")
    public Operation createOperation(@RequestBody OperationDto dto) throws
            OperationTypeInvalidException, OperationRequestFieldsInvalidException,
            RemoteAccountApiUnreachableException, RemoteAccountNotEnoughBalanceException,
            RemoteAccountTypeInaccessibleFromOutsideException, RemoteCustomerStateInvalidException,
            RemoteCustomerApiUnreachableException {
        return inputOperationService.createOperation(dto);
    }

    @GetMapping(value = "/operations")
    public Collection<Operation> getAllOperations() {
        return inputOperationService.getAllOperations();
    }
}
