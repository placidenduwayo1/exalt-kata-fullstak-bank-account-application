package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerAlreadyArchivedException extends Exception {
    public CustomerAlreadyArchivedException(String message) {
        super(message);
    }
}
