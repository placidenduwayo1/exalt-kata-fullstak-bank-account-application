package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountFieldsInvalidException extends Exception{
    public BankAccountFieldsInvalidException(String message) {
        super(message);
    }
}
