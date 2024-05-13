package fr.exalt.businessmicroserviceaccount.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private static final String ID = "1";
    private static final String FIRSTNAME = "placide";
    private static final String LASTNAME = "nduwayo";
    private static final String STATE = "active";
    private static final String EMAIL = "placide.nd@gmail.com";
    private Customer customer = new Customer.CustomerBuilder()
            .customerId("1")
            .firstname(FIRSTNAME)
            .lastname(LASTNAME)
            .email(EMAIL)
            .state(STATE)
            .build();

    @Test
    void testGetCustomer() {
        assertAll("", () -> {
            assertEquals("1", customer.getCustomerId());
            assertEquals("placide", customer.getFirstname());
            assertEquals("nduwayo", customer.getLastname());
            assertEquals("active", customer.getState());
        });
    }
    @Test
    void testSetCustomer(){
        Customer newCustomer = new Customer.CustomerBuilder().build();
        newCustomer.setCustomerId(ID);
        newCustomer.setFirstname(FIRSTNAME);
        newCustomer.setLastname(LASTNAME);
        newCustomer.setState(STATE);
        newCustomer.setEmail(EMAIL);
        assertEquals(customer.toString(), newCustomer.toString());
    }
}