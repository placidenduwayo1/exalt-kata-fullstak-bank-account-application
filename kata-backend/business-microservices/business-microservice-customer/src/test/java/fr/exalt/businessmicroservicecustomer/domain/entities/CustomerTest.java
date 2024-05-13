package fr.exalt.businessmicroservicecustomer.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private static final String ID = "1";
    private static final String FIRSTNAME = "placide";
    private static final String LASTNAME = "nduwayo";
    private static final String STATE = "active";
    private static final String EMAIL = "placide.nd@gmail.com";
    private static final String CREATED_AT = "now";
    private static final Address ADDRESS = new Address.AddressBuilder()
            .addressId("1")
            .streetNum(184)
            .streetName("avenue de liège")
            .poBox(59300)
            .city("valenciennes")
            .country("france")
            .build();
    private final Customer customer = new Customer.CustomerBuilder()
            .customerId(ID)
            .firstname(FIRSTNAME)
            .lastname(LASTNAME)
            .email(EMAIL)
            .state(STATE)
            .createdAt(CREATED_AT)
            .address(ADDRESS)
            .build();
    private final String toStringCustomer = "Customer [" +
            "customer id='" + customer.getCustomerId() + '\'' +
            ", firstname='" + customer.getFirstname() + '\'' +
            ", lastname='" + customer.getLastname() + '\'' +
            ", state='" + customer.getState() + '\'' +
            ", email='" + customer.getEmail() + '\'' +
            ", created at='" + customer.getCreatedAt() + '\'' +
            ", address=" + customer.getAddress() +
            '}';

    @Test
    void testGetCustomer() {
        final Address address = new Address.AddressBuilder()
                .addressId("1")
                .streetNum(184)
                .streetName("avenue de liège")
                .poBox(59300)
                .city("valenciennes")
                .country("france")
                .build();


        assertAll("", () -> {
            assertEquals("1", customer.getCustomerId());
            assertEquals("placide", customer.getFirstname());
            assertEquals("nduwayo", customer.getLastname());
            assertEquals("placide.nd@gmail.com", customer.getEmail());
            assertEquals("active", customer.getState());
            assertEquals("now", customer.getCreatedAt());
            assertEquals(address.toString(), customer.getAddress().toString());
            assertEquals(toStringCustomer, customer.toString());
        });
    }

    @Test
    void testSetCustomer() {
        Customer newCustomer = new Customer.CustomerBuilder().build();
        newCustomer.setCustomerId(ID);
        newCustomer.setAddress(ADDRESS);
        newCustomer.setFirstname(FIRSTNAME);
        newCustomer.setLastname(LASTNAME);
        newCustomer.setEmail(EMAIL);
        newCustomer.setState(STATE);
        newCustomer.setCreatedAt(CREATED_AT);
        assertEquals(customer.toString(), newCustomer.toString());
    }
}