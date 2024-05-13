package fr.exalt.businessmicroserviceaccount.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTypeTest {
    @Test
    void testBankAccountType(){
        assertEquals("current",BankAccountType.CURRENT.getAccountType());
        assertEquals("saving",BankAccountType.SAVING.getAccountType());
    }

}