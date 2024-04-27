package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountStateInvalidException extends Exception {
    public BankAccountStateInvalidException(String message) {
        super(message);
    }
}
