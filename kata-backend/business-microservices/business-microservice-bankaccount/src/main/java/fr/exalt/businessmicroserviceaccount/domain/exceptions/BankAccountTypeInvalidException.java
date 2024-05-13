package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountTypeInvalidException extends Exception{
    public BankAccountTypeInvalidException(String message) {
        super(message);
    }
}
