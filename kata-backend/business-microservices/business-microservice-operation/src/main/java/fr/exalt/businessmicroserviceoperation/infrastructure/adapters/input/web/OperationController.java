package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.entities.TransferOperation;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.domain.ports.input.InputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.TransferDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api-operation")
@AllArgsConstructor
public class OperationController {
    // adapter inputOperationService comme interface entre le domain et l'entrée extérieure
    private final InputOperationService inputOperationService;

    @PostMapping(value = "/operations")
    public Operation createOperation(@RequestBody OperationDto dto) throws
            OperationTypeInvalidException, OperationRequestFieldsInvalidException,
            RemoteBankAccountApiUnreachableException, RemoteBankAccountBalanceException,
            RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteCustomerStateInvalidException,
            RemoteCustomerApiUnreachableException, RemoteBankAccountSuspendedException {
        return inputOperationService.createOperation(dto);
    }

    @GetMapping(value = "/operations")
    public Collection<Operation> getAllOperations() {
        return inputOperationService.getAllOperations();
    }
    @GetMapping(value = "/accounts/{accountId}/operations")
    public Collection<Operation> getAccountOperations(@PathVariable(name = "accountId") String accountId) throws
            RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteBankAccountApiUnreachableException {
        return inputOperationService.getAccountOperations(accountId);
    }
    @PostMapping(value = "/operations/transfer")
    public Map<String, BankAccount> transferBetweenAccounts(@RequestBody TransferDto dto) throws
            RemoteAccountSuspendedException, RemoteBankAccountApiUnreachableException, RemoteCustomerApiUnreachableException,
            RemoteBankAccountBalanceException, RemoteCustomerStateInvalidException, RemoteBankAccountTypeInaccessibleFromOutsideException {
        return inputOperationService.transferBetweenAccounts(dto);
    }
    @GetMapping(value = "/transfers")
    public Collection<TransferOperation> getAllTransfer(){
        return inputOperationService.getAllTransfer();
    }
}
