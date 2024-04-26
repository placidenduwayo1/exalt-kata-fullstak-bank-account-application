package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class AccountFieldsInvalidException extends Exception{
    public AccountFieldsInvalidException(String message) {
        super(message);
    }
}
