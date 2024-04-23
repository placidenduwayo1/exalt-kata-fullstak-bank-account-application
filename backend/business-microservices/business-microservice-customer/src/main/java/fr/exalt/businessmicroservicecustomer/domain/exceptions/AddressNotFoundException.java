package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class AddressNotFoundException extends Exception{
    public AddressNotFoundException(String message) {
        super(message);
    }
}
