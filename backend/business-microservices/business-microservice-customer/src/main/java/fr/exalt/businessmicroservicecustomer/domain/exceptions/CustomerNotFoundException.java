package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
