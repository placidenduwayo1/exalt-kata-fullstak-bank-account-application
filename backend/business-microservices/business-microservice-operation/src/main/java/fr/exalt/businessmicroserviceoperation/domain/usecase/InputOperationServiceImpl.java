package fr.exalt.businessmicroserviceoperation.domain.usecase;

import fr.exalt.businessmicroserviceoperation.domain.entities.Account;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.domain.ports.input.InputOperationService;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.AccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationDto;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class InputOperationServiceImpl implements InputOperationService {
    // adapter outputOperationService comme interface entre le domain et la sortie extérieure
    private final OutputOperationService outputOperationService;

    @Override
    public Operation createOperation(OperationDto operationDto) throws OperationRequestFieldsInvalidException,
            OperationTypeInvalidException, RemoteAccountApiUnreachableException, RemoteAccountNotEnoughBalanceException {

        OperationValidators.formatter(operationDto);

        if (OperationValidators.invalidOperationRequest(operationDto)) {
            throw new OperationRequestFieldsInvalidException(ExceptionsMsg.OPERATION_REQUEST_FIELDS);
        }
        if (!OperationValidators.invalidOperationType(operationDto.getType())) {
            throw new OperationTypeInvalidException(ExceptionsMsg.OPERATION_TYPE);
        }
        Account account = outputOperationService.loadRemoteAccount(operationDto.getAccountId());
        Customer customer = outputOperationService.loaddRemoteCustomer(account.getCustomerId());

        Account updatedAccount = null;

        if (account.getAccountId().equals(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)) {
            throw new RemoteAccountApiUnreachableException(String.format("%s,%n%s", ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE, account));
        } else {
            //operation de retrait
            AccountDto accountDto;
            if (operationDto.getType().equals("retrait") && (OperationValidators.notEnoughBalance(account, operationDto.getMount()))) {
                throw new RemoteAccountNotEnoughBalanceException(ExceptionsMsg.REMOTE_ACCOUNT_BALANCE);
            } else if (operationDto.getType().equals("retrait") && (!OperationValidators.notEnoughBalance(account, operationDto.getMount()))) {
                account.setBalance(-operationDto.getMount());
                accountDto = MapperService.fromTo(account);
                updatedAccount = outputOperationService.updateRemoteAccount(account.getAccountId(), accountDto);
            }
            //operation ed depot sur le compte
            else if (operationDto.getType().equals("depot")) {
                account.setBalance(operationDto.getMount());
                accountDto = MapperService.fromTo(account);
                updatedAccount = outputOperationService.updateRemoteAccount(account.getAccountId(), accountDto);
            }
        }
        assert updatedAccount != null;
        updatedAccount.setCustomer(customer);

        Operation operation = MapperService.fromTo(operationDto);
        operation.setOperationId(UUID.randomUUID().toString());
        operation.setCreatedAt(Timestamp.from(Instant.now()).toString());
        Operation savedOp = outputOperationService.createOperation(operation);
        savedOp.setAccount(updatedAccount);
        return savedOp;
    }

    @Override
    public Collection<Operation> getAllOperations() {
        return outputOperationService.getAllOperations().stream()
                .map(operation -> {
                    Account account = outputOperationService.loadRemoteAccount(operation.getAccountId());
                    operation.setAccount(account);
                    return operation;
                })
                .toList();
    }
}
