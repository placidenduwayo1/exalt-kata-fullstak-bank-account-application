package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies.RemoteAccountServiceProxy;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repositories.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class OutputOperationServiceImplTest {
    @Mock
    private OperationRepository mock1;
    @Mock
    private RemoteAccountServiceProxy mock2;
    @Mock
    private RemoteCustomerServiceProxy mock3;
    @InjectMocks
    private OutputOperationServiceImpl underTest;
    private static final String ID="1";
    private static final String TYPE1="depot";
    private static final String STATE="active";
    private static final double MOUNT=2000;
    private static final String CREATED_AT="now";
    private final CustomerModel customer = CustomerModel.builder()
            .customerId(ID)
            .firstname("placide")
            .lastname("nduwayo")
            .email("placide@gmail.com")
            .state(STATE)
            .build();
    private final BankAccountModel bankAccount = BankAccountModel.builder()
            .accountId(ID)
            .type("current")
            .state(STATE)
            .balance(20000)
            .customerId(ID)
            .customerModel(customer)
            .overdraft(200)
            .build();
    private final OperationModel operation = OperationModel.builder()
            .operationId(ID)
            .type(TYPE1)
            .mount(MOUNT)
            .createdAt(CREATED_AT)
            .accountId(ID)
            .account(bankAccount)
            .build();
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createOperation() {
        //PREPARE
        final Operation op = MapperService.fromTo(operation);
        //EXECUTE
        when(mock2.loadRemoteAccount(operation.getAccountId())).thenReturn(bankAccount);
        when(mock1.save(any(OperationModel.class))).thenReturn(operation);
        Operation actual = underTest.createOperation(op);
        //VERIFY
        assertAll("",()->{
           verify(mock1, atLeast(1)).save(any(OperationModel.class));
           assertNotNull(actual);
           assertEquals(op.toString(),actual.toString());
        });
    }

    @Test
    void getAllOperations() {
        //PREPARE
        final List<OperationModel> models = List.of(operation);
        //EXECUTE
        when(mock1.findAll()).thenReturn(models);
        Collection<Operation> actual = underTest.getAllOperations();
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).findAll();
            assertNotNull(actual);
            assertEquals(1,actual.size());
        });
    }

    @Test
    void loadRemoteAccount() {
        //PREPARE
       final String bankAccountId = "1";
        //EXECUTE
        when(mock2.loadRemoteAccount(bankAccountId)).thenReturn(bankAccount);
        BankAccount actual = underTest.loadRemoteAccount(bankAccountId);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).loadRemoteAccount(bankAccountId);
            assertNotNull(actual);
        });
    }

    @Test
    void loadRemoteCustomer() {
        //PREPARE
        final String customerId = "1";
        //EXECUTE
        when(mock3.loadRemoteCustomer(customerId)).thenReturn(customer);
        Customer actual = underTest.loadRemoteCustomer(customerId);
        //VERIFY
        assertAll("",()->{
            verify(mock3, atLeast(1)).loadRemoteCustomer(customerId);
            assertNotNull(actual);
        });
    }

    @Test
    void updateRemoteAccount() {
        //PREPARE
        BankAccountDto dto = BankAccountDto.builder()
                .balance(2000)
                .customerId(ID)
                .overdraft(200)
                .state(STATE)
                .type(TYPE1)
                .build();
        //EXECUTE
        when(mock2.updateRemoteAccount(bankAccount.getAccountId(), dto)).thenReturn(bankAccount);
        BankAccount actual = underTest.updateRemoteAccount(bankAccount.getAccountId(), dto);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).updateRemoteAccount(bankAccount.getAccountId(), dto);
            assertNotNull(actual);
        });
    }

    @Test
    void getAccountOperations() {
        //PREPARE
        final String bankAccountId = "1";
        List<OperationModel> models = List.of(operation);
        //EXECUTE
        when(mock1.findByAccountId(bankAccountId)).thenReturn(models);
        Collection<Operation> actual = underTest.getAccountOperations(bankAccountId);
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).findByAccountId(bankAccountId);
            assertNotNull(actual);
            assertEquals(1, actual.size());
        });
    }
}