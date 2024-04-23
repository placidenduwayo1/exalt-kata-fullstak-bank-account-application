package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerAlreadyExistsException extends Exception {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
