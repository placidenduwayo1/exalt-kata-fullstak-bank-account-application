package fr.exalt.businessmicroserviceoperation.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {
    private static final String ID ="1";
    private static final String TYPE ="depot";
    private static final double MOUNT =5000;
    private static final String CREATED_AT ="now";
    private static final String STATE ="active";
    private final Customer customer = new Customer.CustomerBuilder()
            .customerId(ID)
            .firstname("FIRSTNAME")
            .lastname("LASTNAME")
            .email("EMAIL")
            .state(STATE)
            .build();
    private final BankAccount bankAccount = new BankAccount.AccountBuilder()
            .customerId(ID)
            .customer(customer)
            .customerId(ID)
            .type(TYPE)
            .balance(5000)
            .overdraft(200)
            .state(STATE)
            .build();

    private final Operation operation = new Operation.OperationBuilder()
            .operationId(ID)
            .account(bankAccount)
            .accountId(ID)
            .createdAt(CREATED_AT)
            .mount(MOUNT)
            .type(TYPE)
            .build();
    @Test
    void testGetOperation(){
        assertAll("",()->{
            assertEquals("1", operation.getOperationId());
            assertEquals("depot", operation.getType());
            assertEquals("now", operation.getCreatedAt());
            assertEquals("1", operation.getAccountId());
            assertEquals(bankAccount.toString(), operation.getBankAccount().toString());
            assertEquals(5000, operation.getMount());
        });
    }
    @Test
    void testSetOperation(){
        Operation newOp = new Operation.OperationBuilder().build();
        newOp.setOperationId(ID);
        newOp.setBankAccount(bankAccount);
        newOp.setAccountId(ID);
        newOp.setCreatedAt(CREATED_AT);
        newOp.setMount(MOUNT);
        newOp.setType(TYPE);

        assertAll("",()->{
            assertEquals(newOp.toString(), operation.toString());
        });
    }
}