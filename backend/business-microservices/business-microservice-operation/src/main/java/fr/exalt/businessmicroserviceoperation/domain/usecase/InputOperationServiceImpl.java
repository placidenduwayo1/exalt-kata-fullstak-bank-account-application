package fr.exalt.businessmicroserviceoperation.domain.usecase;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.domain.ports.input.InputOperationService;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
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

    private void validateOperation(OperationDto dto) throws OperationRequestFieldsInvalidException, OperationTypeInvalidException {
        OperationValidators.formatter(dto);
        if (OperationValidators.invalidOperationRequest(dto)) {
            throw new OperationRequestFieldsInvalidException(ExceptionsMsg.OPERATION_REQUEST_FIELDS);
        }
        if (!OperationValidators.invalidOperationType(dto.getType())) {
            throw new OperationTypeInvalidException(ExceptionsMsg.OPERATION_TYPE);
        }
    }
    @Override
    public Operation createOperation(OperationDto operationDto) throws OperationRequestFieldsInvalidException,
            OperationTypeInvalidException, RemoteAccountApiUnreachableException, RemoteAccountNotEnoughBalanceException,
            RemoteAccountTypeInaccessibleFromOutsideException, RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException {

        validateOperation(operationDto);
        BankAccount bankAccount = outputOperationService.loadRemoteAccount(operationDto.getAccountId());
        BankAccount updatedBankAccount = null;
        Customer customer;
        if (bankAccount.getAccountId().equals(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)) {
            throw new RemoteAccountApiUnreachableException(String.format("%s,%n%s",
                    ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE, bankAccount));
        } else {
            customer = outputOperationService.loaddRemoteCustomer(bankAccount.getCustomerId());
            // verifier le state du customer
            if (customer.getCustomerId().equals(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE)) {
                throw new RemoteCustomerApiUnreachableException(String.format("%s,%n%s",
                        ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE, customer));
            }
            else if(customer.getState().equals("archive")){
                throw new RemoteCustomerStateInvalidException(ExceptionsMsg.REMOTE_CUSTOMER_STATE);
            }

            if (bankAccount.getType().equals("compte-courant")) {
                BankAccountDto bankAccountDto;
                //operation de retrait from the remote account api
                if (operationDto.getType().equals("retrait") &&
                        (OperationValidators.notEnoughBalance(bankAccount, operationDto.getMount()))) {
                    throw new RemoteAccountNotEnoughBalanceException(ExceptionsMsg.REMOTE_ACCOUNT_BALANCE);
                } else if (operationDto.getType().equals("retrait") &&
                        (!OperationValidators.notEnoughBalance(bankAccount, operationDto.getMount()))) {


                    bankAccount.setBalance(-operationDto.getMount());
                    bankAccountDto = MapperService.fromTo(bankAccount);
                    updatedBankAccount = outputOperationService.updateRemoteAccount(bankAccount.getAccountId(), bankAccountDto);
                }
                //operation de depot sur le remote account api
                else if (operationDto.getType().equals("depot")) {
                    bankAccount.setBalance(operationDto.getMount());
                    bankAccountDto = MapperService.fromTo(bankAccount);
                    updatedBankAccount = outputOperationService.updateRemoteAccount(bankAccount.getAccountId(), bankAccountDto);
                }
            } else {
                throw new RemoteAccountTypeInaccessibleFromOutsideException(ExceptionsMsg.REMOTE_ACCOUNT_TYPE);
            }
        }

        assert updatedBankAccount != null;
        updatedBankAccount.setCustomer(customer);

        Operation operation = MapperService.fromTo(operationDto);
        operation.setOperationId(UUID.randomUUID().toString());
        operation.setCreatedAt(Timestamp.from(Instant.now()).toString());
        Operation savedOp = outputOperationService.createOperation(operation);
        savedOp.setAccount(updatedBankAccount);
        return savedOp;
    }

    @Override
    public Collection<Operation> getAllOperations() {
        return outputOperationService.getAllOperations().stream()
                .map(operation -> {
                    BankAccount bankAccount = outputOperationService.loadRemoteAccount(operation.getAccountId());
                    operation.setAccount(bankAccount);
                    return operation;
                })
                .toList();
    }
}
