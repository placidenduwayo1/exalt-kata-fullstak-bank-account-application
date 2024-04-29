package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountGivenSavingException extends Exception{
    public BankAccountGivenSavingException(String message) {
        super(message);
    }
}
