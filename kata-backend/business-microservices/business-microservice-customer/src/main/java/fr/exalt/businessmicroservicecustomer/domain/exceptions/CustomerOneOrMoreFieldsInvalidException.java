package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerOneOrMoreFieldsInvalidException extends Exception{
    public CustomerOneOrMoreFieldsInvalidException(String message) {
        super(message);
    }
}
