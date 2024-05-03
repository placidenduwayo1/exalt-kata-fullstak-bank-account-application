package fr.exalt.businessmicroserviceoperation.domain.usecase;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class InputOperationServiceImplTest {
    @Mock
    private OutputOperationService mock;
    @InjectMocks
    private InputOperationServiceImpl underTest;
    private static final String ID="1";
    private static final String TYPE="depot";
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
            .state("active")
            .balance(10000)
            .overdraft(200)
            .customerId(ID)
            .customer(customer)
            .build();
    private final OperationDto dto = OperationDto.builder()
            .type(TYPE)
            .mount(MOUNT)
            .accountId(ACCOUNT_ID)
            .build();
    private final Operation operation = MapperService.fromTo(dto);

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createOperation() throws OperationTypeInvalidException, RemoteCustomerStateInvalidException,
            OperationRequestFieldsInvalidException, RemoteCustomerApiUnreachableException,
            RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteBankAccountBalanceException,
            RemoteBankAccountApiUnreachableException {
        //PREPARE
        operation.setOperationId(ID);
        operation.setCreatedAt(CREATED_AT);
        final String customerId="1";
        BankAccountDto accountDto = MapperService.fromTo(bankAccount);
        //EXECUTE
        when(mock.loadRemoteCustomer(customerId)).thenReturn(customer);
        when(mock.loadRemoteAccount(dto.getAccountId())).thenReturn(bankAccount);
        when(mock.updateRemoteAccount(bankAccount.getAccountId(), accountDto))
                .thenReturn(bankAccount);
        when(mock.createOperation(any(Operation.class))).thenReturn(operation);
        Operation actual = underTest.createOperation(dto);
        //VERIFY
        assertAll("",()->{
           verify(mock, atLeast(1)).createOperation(any(Operation.class));
           assertNotNull(actual);
        });
    }

    @Test
    void getAllOperations() {
        //PREPARE
        Collection<Operation> operations = List.of(operation);
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
        });
    }

    @Test
    void getAccountOperations() {
        //PREPARE
        //EXECUTE
        //VERIFY
    }

    @Test
    void transferBetweenAccounts() {
        //PREPARE
        //EXECUTE
        //VERIFY
    }
}