package fr.exalt.businessmicroserviceoperation.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTypeTest {
    @Test
    void testOperationType(){
        assertAll("",()->{
            assertEquals("depot",OperationType.DEPOSIT.getType());
            assertEquals("retrait",OperationType.WITHDRAW.getType());
        });
    }

}