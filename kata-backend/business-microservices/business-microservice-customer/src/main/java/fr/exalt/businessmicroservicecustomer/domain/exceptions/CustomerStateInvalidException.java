package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerStateInvalidException extends Exception{
    public CustomerStateInvalidException(String message) {
        super(message);
    }
}
