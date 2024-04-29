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
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Slf4j
public class InputOperationServiceImpl implements InputOperationService {
    // adapter outputOperationService comme interface entre le domain et la sortie extérieure
    private final OutputOperationService outputOperationService;
    private static final String FORMATTER = "%s,%n%s";
    private static final String BANK_ACCOUNT_TYPE_CURRENT = "current";
    private static final String BANK_ACCOUNT_STATE_SUSPEND = "suspended";
    private static final String CUSTOMER_STATE_ARCHIVE = "archive";
    private static final String OPERATION_TYPE_DEPOSIT = "depot";
    private static final String OPERATION_TYPE_RETRAIT = "retrait";


    public InputOperationServiceImpl(OutputOperationService outputOperationService) {
        this.outputOperationService = outputOperationService;
    }

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
            OperationTypeInvalidException, RemoteBankAccountApiUnreachableException, RemoteBankAccountBalanceException,
            RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException{

        validateOperation(operationDto);
        BankAccount bankAccount = outputOperationService.loadRemoteAccount(operationDto.getAccountId());
        BankAccount updatedBankAccount = null;
        Customer customer = outputOperationService.loaddRemoteCustomer(bankAccount.getCustomerId());
        if (bankAccount.getAccountId().equals(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)
                || bankAccount.getState().equals(BANK_ACCOUNT_STATE_SUSPEND)) {
            throw new RemoteBankAccountApiUnreachableException(String.format(FORMATTER,
                    ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE, bankAccount));
        } else {
            if (customer.getCustomerId().equals(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE)) {
                throw new RemoteCustomerApiUnreachableException(String.format(FORMATTER,
                        ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE, customer));
            }
            else if(customer.getState().equals(CUSTOMER_STATE_ARCHIVE)){
                throw new RemoteCustomerStateInvalidException(ExceptionsMsg.REMOTE_CUSTOMER_STATE);
            }
            else if (bankAccount.getType().equals(BANK_ACCOUNT_TYPE_CURRENT)) {
                    BankAccountDto bankAccountDto;
                    //operation de retrait from the remote account api
                    if (operationDto.getType().equals(OPERATION_TYPE_RETRAIT) &&
                            (!OperationValidators.enoughBalance(bankAccount, operationDto.getMount()))) {
                        throw new RemoteBankAccountBalanceException(ExceptionsMsg.REMOTE_ACCOUNT_BALANCE);
                    } else if (operationDto.getType().equals(OPERATION_TYPE_RETRAIT) &&
                            (OperationValidators.enoughBalance(bankAccount, operationDto.getMount()))) {

                        bankAccount.setBalance(-operationDto.getMount());
                        bankAccountDto = MapperService.fromTo(bankAccount);
                        updatedBankAccount = outputOperationService.updateRemoteAccount(bankAccount.getAccountId(), bankAccountDto);
                    }
                    //operation de depot sur le remote account api
                    else if (operationDto.getType().equals(OPERATION_TYPE_DEPOSIT)) {
                        bankAccount.setBalance(operationDto.getMount());
                        bankAccountDto = MapperService.fromTo(bankAccount);
                        updatedBankAccount = outputOperationService.updateRemoteAccount(bankAccount.getAccountId(), bankAccountDto);
                    }
                } else {
                    throw new RemoteBankAccountTypeInaccessibleFromOutsideException(ExceptionsMsg.REMOTE_ACCOUNT_NOT_ACCESSIBLE_FROM_OUTSIDE);
                }

        }
        assert updatedBankAccount != null;
        updatedBankAccount.setCustomer(customer);
        Operation operation = MapperService.fromTo(operationDto);
        operation.setOperationId(UUID.randomUUID().toString());
        operation.setCreatedAt(Timestamp.from(Instant.now()).toString());
        operation.setAccount(updatedBankAccount);
        outputOperationService.createOperation(operation);
        return operation;
    }

    @Override
    public Collection<Operation> getAllOperations() {
        return outputOperationService.getAllOperations().stream()
                .map(operation -> {
                    BankAccount bankAccount = outputOperationService.loadRemoteAccount(operation.getAccountId());
                    Customer customer = outputOperationService.loaddRemoteCustomer(bankAccount.getCustomerId());
                    bankAccount.setCustomer(customer);
                    operation.setAccount(bankAccount);
                    return operation;
                })
                .toList();
    }
}
