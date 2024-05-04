package fr.exalt.businessmicroserviceoperation.domain.usecase;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.TransferDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
@Slf4j
class InputOperationServiceImplTest {
    @Mock
    private OutputOperationService mock;
    @InjectMocks
    private InputOperationServiceImpl underTest;
    private static final String ID="1";
    private static final String TYPE="depot";
    private static final String STATE="active";
    private static final double MOUNT = 2500;
    private static final String ACCOUNT_ID="1";
    private static final String CREATED_AT="now";
    private final Customer customer = new Customer.CustomerBuilder()
            .firstname("placide")
            .lastname("nduwayo")
            .customerId(ID)
            .email("placide.nd@gmail.com")
            .state("active")
            .build();
    private final BankAccount bankAccount = new BankAccount.AccountBuilder()
            .accountId(ID)
            .type("current")
            .state(STATE)
            .balance(10000)
            .overdraft(200)
            .customerId(ID)
            .customer(customer)
            .build();
    private final OperationDto dtoDepot = OperationDto.builder()
            .type(TYPE)
            .mount(MOUNT)
            .accountId(ACCOUNT_ID)
            .build();
    private final OperationDto dtoRetrait = OperationDto.builder()
            .type("retrait")
            .mount(MOUNT)
            .accountId(ACCOUNT_ID)
            .build();
    private final Operation operationDepot = MapperService.fromTo(dtoDepot);

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createOperationDepot() throws OperationTypeInvalidException, RemoteCustomerStateInvalidException,
            OperationRequestFieldsInvalidException, RemoteCustomerApiUnreachableException,
            RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteBankAccountBalanceException,
            RemoteBankAccountApiUnreachableException {
        //PREPARE
        operationDepot.setOperationId(ID);
        operationDepot.setCreatedAt(CREATED_AT);
        final String customerId="1";
        BankAccountDto accountDto = MapperService.fromTo(bankAccount);
        //EXECUTE
        when(mock.loadRemoteCustomer(customerId)).thenReturn(customer);
        when(mock.loadRemoteAccount(dtoDepot.getAccountId())).thenReturn(bankAccount);
        when(mock.updateRemoteAccount(bankAccount.getAccountId(), accountDto))
                .thenReturn(bankAccount);
        when(mock.createOperation(any(Operation.class))).thenReturn(operationDepot);
        Operation actual = underTest.createOperation(dtoDepot);
        //VERIFY
        assertAll("",()->{
           verify(mock, atLeast(1)).createOperation(any(Operation.class));
           verify(mock, atLeast(1)).updateRemoteAccount(anyString(), any(BankAccountDto.class));
           assertNotNull(actual);
           log.info("{}",actual);
        });
    }
    @Test
    void createOperationRetrait() throws OperationTypeInvalidException, RemoteCustomerStateInvalidException,
            OperationRequestFieldsInvalidException, RemoteCustomerApiUnreachableException,
            RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteBankAccountBalanceException,
            RemoteBankAccountApiUnreachableException {
        //PREPARE
        final Operation operationRetrait = MapperService.fromTo(dtoRetrait);
        operationRetrait.setCreatedAt(CREATED_AT);
        final String customerId="1";
        //EXECUTE
        when(mock.loadRemoteCustomer(customerId)).thenReturn(customer);
        when(mock.loadRemoteAccount(dtoDepot.getAccountId())).thenReturn(bankAccount);
        when(mock.updateRemoteAccount(anyString(), any(BankAccountDto.class)))
                .thenReturn(bankAccount);
        when(mock.createOperation(any(Operation.class))).thenReturn(operationRetrait);
        Operation actual = underTest.createOperation(dtoRetrait);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).createOperation(any(Operation.class));
            assertNotNull(actual);
            log.info("{}",actual);
        });
    }

    @Test
    void getAllOperations() {
        //PREPARE
        final Collection<Operation> operations = List.of(operationDepot);
        //EXECUTE
        operations.forEach(operation1 -> {
            when(mock.loadRemoteAccount(operation1.getAccountId())).thenReturn(bankAccount);
        });
        when(mock.getAllOperations()).thenReturn(operations);
        Collection<Operation> actual = underTest.getAllOperations();
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getAllOperations();
            assertNotNull(actual);
            assertFalse(actual.isEmpty());
            log.info("{}",actual);
        });
    }

    @Test
    void getAccountOperations() throws RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteBankAccountApiUnreachableException {
        //PREPARE
        final Collection<Operation> operations = List.of(operationDepot);
        //EXECUTE
        when(mock.loadRemoteAccount(ID)).thenReturn(bankAccount);
        when(mock.getAccountOperations(ID)).thenReturn(operations);
        Collection<Operation> actual = underTest.getAccountOperations(ID);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getAccountOperations(ID);
            assertEquals(1, actual.size());
            log.info("{}",actual);
        });
    }

    @Test
    void transferBetweenAccounts() throws RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException,
            RemoteAccountSuspendedException, RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteBankAccountBalanceException,
            RemoteBankAccountApiUnreachableException {
        //PREPARE
        BankAccount origin = bankAccount;
        final BankAccount destination = new BankAccount.AccountBuilder()
                .accountId("2")
                .type("saving")
                .state(STATE)
                .balance(10000)
                .customerId(ID)
                .customer(customer)
                .build();
        final TransferDto transferDto = TransferDto.builder()
                .origin(ID)
                .destination(destination.getAccountId())
                .mount(1000)
                .build();
        final BankAccount updatedOrigin = new BankAccount.AccountBuilder()
                .accountId(ID)
                .customer(customer)
                .customerId(ID)
                .balance(9000)
                .type(TYPE)
                .state(STATE)
                .build();
        final BankAccount updatedDestination = new BankAccount.AccountBuilder()
                .accountId("2")
                .customer(customer)
                .customerId(ID)
                .balance(11000)
                .type(TYPE)
                .state(STATE)
                .build();
        log.info("{}", updatedDestination);
        log.info("{}", updatedOrigin);
        //EXECUTE
        when(mock.loadRemoteAccount(transferDto.getOrigin())).thenReturn(origin);
        when(mock.loadRemoteAccount(transferDto.getDestination())).thenReturn(destination);
        when(mock.loadRemoteCustomer(origin.getCustomerId())).thenReturn(customer);
        when(mock.loadRemoteCustomer(destination.getCustomerId())).thenReturn(customer);
        when(mock.updateRemoteAccount(anyString(), any(BankAccountDto.class)))
                .thenReturn(updatedOrigin);
        when(mock.updateRemoteAccount(anyString(), any(BankAccountDto.class)))
                .thenReturn(updatedDestination);
        Map<String, BankAccount> actual = underTest.transferBetweenAccounts(transferDto);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).loadRemoteAccount(transferDto.getOrigin());
            verify(mock, atLeast(1)).loadRemoteAccount(transferDto.getDestination());
            verify(mock, atLeast(1)).loadRemoteCustomer(origin.getCustomerId());
            verify(mock,atLeast(1)).loadRemoteCustomer(destination.getCustomerId());
            verify(mock, atLeast(1)).updateRemoteAccount(anyString(), any(BankAccountDto.class));
            verify(mock, atLeast(1)).updateRemoteAccount(anyString(), any(BankAccountDto.class));
            assertNotNull(actual);
        });
    }
}