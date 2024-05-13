package fr.exalt.businessmicroserviceoperation.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private static final String ID = "1";
    private static final String TYPE = "current";
    private static final String STATE = "active";
    private static final double BALANCE = 2000;
    private static final String CUSTOMER_ID = "1";
    private static final String FIRSTNAME = "placide";
    private static final String LASTNAME = "nduwayo";
    private static final String EMAIL = "placide.nd@gmail.com";
    private final Customer customer = new Customer.CustomerBuilder()
            .customerId(ID)
            .firstname(FIRSTNAME)
            .lastname(LASTNAME)
            .email(EMAIL)
            .state(STATE)
            .build();
    private final BankAccount bankAccount = new BankAccount.AccountBuilder()
            .accountId(ID)
            .type(TYPE)
            .state(STATE)
            .balance(BALANCE)
            .overdraft(200)
            .customerId(ID)
            .customer(customer)
            .build();

    @Test
    void testCurrentBankAccount() {
        bankAccount.setAccountId(ID);
        bankAccount.setType(TYPE);
        bankAccount.setState(STATE);
        bankAccount.setBalance(BALANCE);
        bankAccount.setOverdraft(200);
        bankAccount.setCustomerId(CUSTOMER_ID);
        bankAccount.setCustomer(customer);
        assertAll("", () -> {
            assertEquals("1", bankAccount.getAccountId());
            assertEquals("current", bankAccount.getType());
            assertEquals("active", bankAccount.getState());
            assertEquals(2000, bankAccount.getBalance());
            assertEquals(200, bankAccount.getOverdraft());
            assertEquals("1", bankAccount.getCustomerId());
            assertEquals(customer, bankAccount.getCustomer());
        });
    }
}