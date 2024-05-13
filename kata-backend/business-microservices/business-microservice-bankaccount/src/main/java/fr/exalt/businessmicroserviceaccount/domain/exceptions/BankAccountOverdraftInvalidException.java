package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountOverdraftInvalidException extends Exception{
    public BankAccountOverdraftInvalidException(String message) {
        super(message);
    }
}
