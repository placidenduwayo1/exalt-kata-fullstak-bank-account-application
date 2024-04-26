package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class AccountStateInvalidException extends Exception {
    public AccountStateInvalidException(String message) {
        super(message);
    }
}
