package fr.exalt.businessmicroserviceaccount.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountStateTest {

    @Test
    void testBankAccountState(){
        assertEquals("active",BankAccountState.ACTIVE.getState());
        assertEquals("suspended",BankAccountState.SUSPENDED.getState());
    }

}