package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerEmailInvalidException extends Exception{
    public CustomerEmailInvalidException(String message) {
        super(message);
    }
}
