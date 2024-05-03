package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputBankAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountInterestRateDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountOverdraftDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountSwitchStatedDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class BankAccountControllerTest {
    @Mock
    private InputBankAccountService mock;
    @InjectMocks
    private BankAccountController underTest;
    private final BankAccountDto dto1 = BankAccountDto.builder()
            .type("current")
            .balance(2000)
            .customerId("1")
            .build();
    private final BankAccountDto dto2 = BankAccountDto.builder()
            .type("saving")
            .balance(2000)
            .customerId("1")
            .build();
    private final Customer customer = new Customer.CustomerBuilder()
            .customerId("1")
            .firstname("placide")
            .lastname("nduwayo")
            .email("placide.nd@gmail.com")
            .state("active")
            .build();
    private CurrentBankAccount current;
    private SavingBankAccount saving;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        current = new CurrentBankAccount.CurrentAccountBuilder()
                .overdraft(200)
                .build();
        current.setAccountId("1");
        current.setType(dto1.getType());
        current.setState("active");
        current.setBalance(dto1.getBalance());
        current.setCreatedAt("now");
        current.setCustomer(customer);

        saving = new SavingBankAccount.SavingAccountBuilder()
                .interestRate(5.0)
                .build();
        saving.setAccountId("2");
        saving.setType(dto2.getType());
        saving.setState("active");
        saving.setBalance(dto2.getBalance());
        saving.setCreatedAt("now");
        saving.setCustomer(customer);
    }

    @Test
    void createAccount() throws BankAccountStateInvalidException, RemoteCustomerStateInvalidException,
            BankAccountTypeInvalidException, RemoteCustomerApiUnreachableException, BankAccountFieldsInvalidException {

        //EXECUTE
        when(mock.createAccount(dto1)).thenReturn(current);
        when(mock.createAccount(dto2)).thenReturn(saving);
        BankAccount actual1 = underTest.createAccount(dto1);
        BankAccount actual2 = underTest.createAccount(dto2);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).createAccount(dto1);
            verify(mock, atLeast(1)).createAccount(dto2);
            assertNotNull(actual1);
            assertNotNull(actual2);
            assertEquals(actual1.getCustomer(), actual2.getCustomer());
        });
    }

    @Test
    void getAllAccounts() {
        //PREPARE
        Collection<BankAccount> accounts = List.of(saving, current);
        //EXECUTE
        when(mock.getAllAccounts()).thenReturn(accounts);
        Collection<BankAccount> actual = underTest.getAllAccounts();
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getAllAccounts();
            assertFalse(actual.isEmpty());
            assertEquals(2, actual.size());
        });
    }

    @Test
    void getAccount() throws BankAccountNotFoundException {
        //PREPARE
        final String id1 = "1";
        final String id2 = "2";
        //EXECUTE
        when(mock.getAccount(id1)).thenReturn(current);
        when(mock.getAccount(id2)).thenReturn(saving);
        BankAccount actual1 = underTest.getAccount(id1);
        BankAccount actual2 = underTest.getAccount(id2);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getAccount(id1);
            verify(mock, atLeast(1)).getAccount(id2);
            assertInstanceOf(CurrentBankAccount.class, actual1);
            assertInstanceOf(SavingBankAccount.class, actual2);
            assertNotNull(actual1);
            assertNotNull(actual2);
        });
    }

    @Test
    void getAccountOfGivenCustomer() {
        //PREPARE
        Collection<BankAccount> accounts = List.of(saving, current);

        //EXECUTE
        when(mock.getAccountOfGivenCustomer(customer.getCustomerId())).thenReturn(accounts);
        Collection<BankAccount> actual = underTest.getAccountOfGivenCustomer(customer.getCustomerId());
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getAccountOfGivenCustomer(customer.getCustomerId());
            assertFalse(actual.isEmpty());
            assertEquals(2, actual.size());
            assertTrue(actual.contains(saving));
            assertTrue(actual.contains(current));
        });
    }

    @Test
    void updateAccount() throws BankAccountNotFoundException, BankAccountStateInvalidException,
            RemoteCustomerStateInvalidException, BankAccountTypeInvalidException, RemoteCustomerApiUnreachableException,
            BankAccountFieldsInvalidException {
        //PREPARE
        final double balance = 3000;
        final CurrentBankAccount newCurrent = current;
        current.setBalance(current.getBalance() + balance);
        final SavingBankAccount newSaving = saving;
        saving.setBalance(saving.getBalance() + balance);

        //EXECUTE
        when(mock.updateAccount(current.getAccountId(), dto1)).thenReturn(newCurrent);
        when(mock.updateAccount(saving.getAccountId(), dto2)).thenReturn(newSaving);
        BankAccount actual1 = underTest.updateAccount(current.getAccountId(), dto1);
        BankAccount actual2 = underTest.updateAccount(saving.getAccountId(), dto2);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).updateAccount(current.getAccountId(), dto1);
            verify(mock, atLeast(1)).updateAccount(saving.getAccountId(), dto2);
            assertNotNull(actual1);
            assertNotNull(actual2);
            assertEquals(actual1.getCustomer(), actual2.getCustomer());
            assertEquals(5000, actual1.getBalance());
            assertEquals(actual1.getBalance(), actual2.getBalance());
        });
    }

    @Test
    void switchAccountState() throws BankAccountNotFoundException, BankAccountStateInvalidException,
            RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException, BankAccountSameStateException {
        //PREPARE
        final BankAccountSwitchStatedDto dto = BankAccountSwitchStatedDto.builder()
                .accountId("1")
                .state("suspended")
                .build();
        current.setState("suspended");
        //EXECUTE
        when(mock.switchAccountState(dto)).thenReturn(current);
        BankAccount actual = underTest.switchAccountState(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).switchAccountState(dto);
            assertEquals("suspended", actual.getState());
        });
    }

    @Test
    void changeOverdraft() throws BankAccountNotFoundException, RemoteCustomerStateInvalidException,
            BankAccountSuspendException, RemoteCustomerApiUnreachableException, BankAccountOverdraftInvalidException,
            BankAccountTypeNotAcceptedException {
        //PREPARE
        final double overdraft = 300;
        final double balance = 5000;
        current.setOverdraft(overdraft);
        current.setBalance(current.getBalance() + balance);
        BankAccountOverdraftDto dto = BankAccountOverdraftDto.builder()
                .overdraft(overdraft)
                .accountId("1")
                .build();
        //EXECUTE
        when(mock.changeOverdraft(dto)).thenReturn(current);
        BankAccount actual = underTest.changeOverdraft(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).changeOverdraft(dto);
            assertNotNull(actual);
            assertEquals(7000, actual.getBalance());
            log.info("{}", actual);
        });
    }

    @Test
    void changeInterestRate() throws BankAccountNotFoundException, BankAccountSuspendException, RemoteCustomerStateInvalidException,
            RemoteCustomerApiUnreachableException, BankAccountTypeNotAcceptedException {
        //PREPARE
        final double rate = 7.5;
        final double balance = 5000;
        saving.setBalance(saving.getBalance() + balance);
        saving.setInterestRate(rate);
        BankAccountInterestRateDto dto = BankAccountInterestRateDto.builder()
                .interestRate(rate)
                .accountId("2")
                .build();
        //EXECUTE
        when(mock.changeInterestRate(dto)).thenReturn(saving);
        BankAccount actual = underTest.changeInterestRate(dto);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).changeInterestRate(dto);
            assertNotNull(actual);
            assertEquals(7000, actual.getBalance());
            log.info("{}", actual);
        });
    }
}