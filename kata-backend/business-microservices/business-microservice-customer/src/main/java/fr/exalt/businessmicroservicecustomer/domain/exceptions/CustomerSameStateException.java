package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerSameStateException extends Exception {
    public CustomerSameStateException(String message) {
        super(message);
    }
}
