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
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.TransferDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Slf4j
public class InputOperationServiceImpl implements InputOperationService {
    private final OutputOperationService outputOperationService;/* outputOperationService is an interface adapter
      entre le domain et la sortie extérieure, cette adapter est utilisé  pour tout qui est à l'extérieur du domain en output */
    private static final String FORMATTER = "%s,%n%s";
    private static final String BANK_ACCOUNT_TYPE_SAVING = "saving";
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
            RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException {

        validateOperation(operationDto);
        BankAccount bankAccount = outputOperationService.loadRemoteAccount(operationDto.getAccountId());
        Customer customer = outputOperationService.loadRemoteCustomer(bankAccount.getCustomerId());
        if (bankAccount.getAccountId().equals(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)
                || bankAccount.getState().equals(BANK_ACCOUNT_STATE_SUSPEND)) {
            throw new RemoteBankAccountApiUnreachableException(String.format(FORMATTER,
                    ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE, bankAccount));
        }
        if (customer.getCustomerId().equals(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE)) {
            throw new RemoteCustomerApiUnreachableException(String.format(FORMATTER,
                    ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE, customer));
        }

        if (customer.getState().equals(CUSTOMER_STATE_ARCHIVE)){
            throw new RemoteCustomerStateInvalidException(ExceptionsMsg.REMOTE_CUSTOMER_STATE);
        }

        if(bankAccount.getType().equals(BANK_ACCOUNT_TYPE_SAVING)){
            throw new RemoteBankAccountTypeInaccessibleFromOutsideException(ExceptionsMsg.REMOTE_ACCOUNT_NOT_ACCESSIBLE_FROM_OUTSIDE);
        }

        BankAccountDto bankAccountDto;
        //operation de retrait from the remote account api
        Operation operation = MapperService.fromTo(operationDto);
        if (operationDto.getType().equals(OPERATION_TYPE_RETRAIT) &&
                (!OperationValidators.enoughBalance(bankAccount, operationDto.getMount()))) {
            throw new RemoteBankAccountBalanceException(ExceptionsMsg.REMOTE_ACCOUNT_BALANCE);
        } else if (operationDto.getType().equals(OPERATION_TYPE_RETRAIT) &&
                (OperationValidators.enoughBalance(bankAccount, operationDto.getMount()))) {

            bankAccount.setBalance(-operationDto.getMount());
            bankAccountDto = MapperService.fromTo(bankAccount);
            BankAccount updatedBankAccount = outputOperationService.updateRemoteAccount(bankAccount.getAccountId(), bankAccountDto);
            updatedBankAccount.setCustomer(customer);

            operation.setOperationId(UUID.randomUUID().toString());
            operation.setCreatedAt(Timestamp.from(Instant.now()).toString());
            Operation savedOp = outputOperationService.createOperation(operation);
            savedOp.setBankAccount(updatedBankAccount);
            return savedOp;
        }
        //operation de depot sur le remote account api
        else if (operationDto.getType().equals(OPERATION_TYPE_DEPOSIT)) {
            bankAccount.setBalance(operationDto.getMount());
            bankAccountDto = MapperService.fromTo(bankAccount);
            BankAccount updatedBankAccount = outputOperationService.updateRemoteAccount(bankAccount.getAccountId(), bankAccountDto);
            operation.setOperationId(UUID.randomUUID().toString());
            operation.setCreatedAt(Timestamp.from(Instant.now()).toString());
            Operation savedOp = outputOperationService.createOperation(operation);
            savedOp.setBankAccount(updatedBankAccount);
            return savedOp;

        }

        return null;
    }

    @Override
    public Collection<Operation> getAllOperations() {
        return setOperationDependencies(outputOperationService.getAllOperations());
    }

    @Override
    public Collection<Operation> getAccountOperations(String accountId) throws RemoteBankAccountApiUnreachableException, RemoteBankAccountTypeInaccessibleFromOutsideException {
        BankAccount account = outputOperationService.loadRemoteAccount(accountId);
        if (OperationValidators.remoteAccountApiUnreachable(account.getAccountId())) {
            throw new RemoteBankAccountApiUnreachableException(String.format(FORMATTER, ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE, account));
        } else if (account.getType().equals(BANK_ACCOUNT_TYPE_SAVING)) {
            throw new RemoteBankAccountTypeInaccessibleFromOutsideException(ExceptionsMsg.REMOTE_ACCOUNT_NOT_ACCESSIBLE_FROM_OUTSIDE);
        } else {
            return setOperationDependencies(outputOperationService.getAccountOperations(accountId));
        }
    }

    @Override
    public Map<String, Object> transferBetweenAccounts(TransferDto dto) throws RemoteBankAccountApiUnreachableException,
            RemoteAccountSuspendedException, RemoteCustomerApiUnreachableException, RemoteBankAccountBalanceException,
            RemoteCustomerStateInvalidException, RemoteBankAccountTypeInaccessibleFromOutsideException {
        // call output adapter to get remote bank accounts from bank account api
        BankAccount origin = outputOperationService.loadRemoteAccount(dto.getOrigin());
        BankAccount destination = outputOperationService.loadRemoteAccount(dto.getDestination());

        if (origin.getAccountId().equals(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)
                || destination.getAccountId().equals(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)) {
            throw new RemoteBankAccountApiUnreachableException(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE);
        }

        if (origin.getState().equals(BANK_ACCOUNT_STATE_SUSPEND) || destination.getState().equals(BANK_ACCOUNT_STATE_SUSPEND)) {
            throw new RemoteAccountSuspendedException(ExceptionsMsg.REMOTE_BANK_ACCOUNT_SUSPENDED);
        }
        // call output adapter to get remote customers from customer api
        Customer customer1 = outputOperationService.loadRemoteCustomer(origin.getCustomerId());
        Customer customer2 = outputOperationService.loadRemoteCustomer(destination.getCustomerId());
        if (customer1.getCustomerId().equals(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE)
                || customer2.getCustomerId().equals(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE)) {
            throw new RemoteCustomerApiUnreachableException(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE);
        }
        if (customer1.getState().equals(CUSTOMER_STATE_ARCHIVE)) {
            throw new RemoteCustomerStateInvalidException(ExceptionsMsg.REMOTE_CUSTOMER_STATE);
        }
        if (origin.getBalance() <= dto.getMount()) {
            throw new RemoteBankAccountBalanceException(ExceptionsMsg.REMOTE_ACCOUNT_BALANCE);
        }

        // money transfer between current and saving only possible for the same customer
        if ((origin.getType().equals(BANK_ACCOUNT_TYPE_SAVING) && !customer2.getCustomerId().equals(origin.getCustomerId()))
                || (destination.getType().equals(BANK_ACCOUNT_TYPE_SAVING) && !customer1.getCustomerId().equals(destination.getCustomerId()))) {
            throw new RemoteBankAccountTypeInaccessibleFromOutsideException(ExceptionsMsg.REMOTE_ACCOUNT_NOT_ACCESSIBLE_FROM_OUTSIDE);
        }

        origin.setBalance(-dto.getMount());
        BankAccountDto mapped1 = MapperService.fromTo(origin);
        // call output adapter to update origin account balance from remote bank account
        BankAccount updatedOrigin = outputOperationService.updateRemoteAccount(origin.getAccountId(), mapped1);
        updatedOrigin.setCustomer(customer1);
        destination.setBalance(dto.getMount());
        BankAccountDto mapped2 = MapperService.fromTo(destination);
        // call output adapter to update destination account balance from remote bank account
        BankAccount updatedDestination = outputOperationService.updateRemoteAccount(destination.getAccountId(), mapped2);
        updatedDestination.setCustomer(customer2);

        return Map.of("origin", updatedOrigin, "destination", updatedDestination);

    }

    private Collection<Operation> setOperationDependencies(Collection<Operation> operations) {
        return operations.stream()
                .map(operation -> {
                    BankAccount bankAccount = outputOperationService.loadRemoteAccount(operation.getAccountId());
                    Customer customer = outputOperationService.loadRemoteCustomer(bankAccount.getCustomerId());
                    bankAccount.setCustomer(customer);
                    operation.setBankAccount(bankAccount);
                    return operation;
                })
                .toList();
    }
}
