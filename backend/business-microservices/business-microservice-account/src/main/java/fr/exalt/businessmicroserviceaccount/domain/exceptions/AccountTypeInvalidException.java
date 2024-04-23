package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class AccountTypeInvalidException extends Exception{
    public AccountTypeInvalidException(String message) {
        super(message);
    }
}
