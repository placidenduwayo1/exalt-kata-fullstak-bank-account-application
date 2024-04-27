package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountAlreadySuspendException extends Exception{
    public BankAccountAlreadySuspendException(String message) {
        super(message);
    }
}
