package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.domain.ports.input.InputOperationService;
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
class OperationControllerTest {
    @Mock
    private InputOperationService mock;
    @InjectMocks
    private OperationController underTest;
    private static final String ID="1";
    private static final String TYPE="depot";
    private static final double MOUNT=2000;
    private final OperationDto dto = OperationDto.builder()
            .accountId(ID)
            .type(TYPE)
            .mount(MOUNT)
            .build();

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createOperation() throws OperationTypeInvalidException, RemoteCustomerStateInvalidException,
            OperationRequestFieldsInvalidException, RemoteCustomerApiUnreachableException, RemoteBankAccountTypeInaccessibleFromOutsideException,
            RemoteBankAccountBalanceException, RemoteBankAccountApiUnreachableException {
        //PREPARE
        final Operation operation = MapperService.fromTo(dto);
        //EXECUTE
        when(mock.createOperation(dto)).thenReturn(operation);
        Operation actual = underTest.createOperation(dto);
        //VERIFY
        assertAll("",()->{
           verify(mock, atLeast(1)).createOperation(dto);
           assertNotNull(actual);
           assertEquals(operation, actual);
        });
    }

    @Test
    void getAllOperations() {
        //PREPARE
        Operation operation = MapperService.fromTo(dto);
        Collection<Operation> operations = List.of(operation);
        //EXECUTE
        when(mock.getAllOperations()).thenReturn(operations);
        Collection<Operation> actual = underTest.getAllOperations();
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getAllOperations();
            assertFalse(actual.isEmpty());
            assertTrue(actual.contains(operation));
            assertEquals(operations.size(), actual.size());
        });
    }

    @Test
    void getAccountOperations() throws RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteBankAccountApiUnreachableException {
        //PREPARE
        final String accountId="1";
        final Operation operation = MapperService.fromTo(dto);
        //EXECUTE
        when(mock.getAccountOperations(accountId)).thenReturn(List.of(operation));
        Collection<Operation> actual =underTest.getAccountOperations(accountId);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getAccountOperations(accountId);
            assertFalse(actual.isEmpty());
            assertTrue(actual.contains(operation));
        });
    }

    @Test
    void transferBetweenAccounts() throws RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException,
            RemoteAccountSuspendedException, RemoteBankAccountTypeInaccessibleFromOutsideException, RemoteBankAccountBalanceException,
            RemoteBankAccountApiUnreachableException {
        //PREPARE
        final BankAccount origin = new BankAccount.AccountBuilder()
                .accountId("1")
                .overdraft(200)
                .balance(0)
                .state("active")
                .type("current")
                .customerId("1")
                .build();
        final BankAccount destination = new BankAccount.AccountBuilder()
                .accountId("2")
                .balance(50000)
                .state("active")
                .type("saving")
                .customerId("1")
                .build();
        final TransferDto transferDto = TransferDto.builder()
                .mount(20000)
                .origin(origin.getAccountId())
                .destination(destination.getAccountId())
                .build();
        //EXECUTE
        when(mock.transferBetweenAccounts(transferDto))
                .thenReturn(Map.of("origin",origin, "destination", destination));
        Map<String, BankAccount> actual = underTest.transferBetweenAccounts(transferDto);
        BankAccount bankAccount = actual.get("destination");
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).transferBetweenAccounts(transferDto);
            assertNotNull(actual);
            assertEquals(50000,bankAccount.getBalance());
            log.info("{}",actual);
        });
    }
}