package fr.exalt.businessmicroservicecustomer.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerStateTest {
    @Test
    void testCustomerState(){
        assertEquals("active",CustomerState.ACTIVE.getState());
        assertEquals("archive",CustomerState.ARCHIVE.getState());
    }
}