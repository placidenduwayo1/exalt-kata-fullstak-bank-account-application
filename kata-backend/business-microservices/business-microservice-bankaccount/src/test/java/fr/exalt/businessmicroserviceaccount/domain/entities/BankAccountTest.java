package fr.exalt.businessmicroserviceaccount.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountTest {
    private static final String ID="1";
    private static final String TYPE="current";
    private static final String STATE="active";
    private static final double BALANCE= 2000;
    private static final String CREATED_AT="now";
    private static final String CUSTOMER_ID ="1";
    private final Customer customer = new Customer.CustomerBuilder().build();
    private final CurrentBankAccount currentBankAccount = new CurrentBankAccount.CurrentAccountBuilder()
            .build();
    private final SavingBankAccount savingBankAccount = new SavingBankAccount.SavingAccountBuilder()
            .build();
    @Test
    void testCurrentBankAccount(){
        currentBankAccount.setAccountId(ID);
        currentBankAccount.setType(TYPE);
        currentBankAccount.setState(STATE);
        currentBankAccount.setBalance(BALANCE);
        currentBankAccount.setOverdraft(200);
        currentBankAccount.setCustomerId(CUSTOMER_ID);
        currentBankAccount.setCustomer(customer);
        currentBankAccount.setCreatedAt(CREATED_AT);
        assertAll("",()->{
            assertEquals("1", currentBankAccount.getAccountId());
            assertEquals("current", currentBankAccount.getType());
            assertEquals("active", currentBankAccount.getState());
            assertEquals(2000, currentBankAccount.getBalance());
            assertEquals(200, currentBankAccount.getOverdraft());
            assertEquals("now", currentBankAccount.getCreatedAt());
            assertEquals("1", currentBankAccount.getCustomerId());
            assertEquals(customer.toString(), currentBankAccount.getCustomer().toString());
        });
    }
    @Test
    void testSavingBankAccount(){
        savingBankAccount.setAccountId(ID);
        savingBankAccount.setType("saving");
        savingBankAccount.setState(STATE);
        savingBankAccount.setBalance(BALANCE);
        savingBankAccount.setInterestRate(3.5);
        savingBankAccount.setCustomerId(CUSTOMER_ID);
        savingBankAccount.setCustomer(customer);
        savingBankAccount.setCreatedAt(CREATED_AT);
        assertAll("",()-> {
            assertEquals("1", savingBankAccount.getAccountId());
            assertEquals("saving", savingBankAccount.getType());
            assertEquals("active", savingBankAccount.getState());
            assertEquals(2000, savingBankAccount.getBalance());
            assertEquals(3.5, savingBankAccount.getInterestRate());
            assertEquals("now", savingBankAccount.getCreatedAt());
            assertEquals("1", savingBankAccount.getCustomerId());
            assertEquals(customer.toString(), savingBankAccount.getCustomer().toString());
        });
    }
}