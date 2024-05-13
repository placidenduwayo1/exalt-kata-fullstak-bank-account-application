package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountSuspendException extends Exception{
    public BankAccountSuspendException(String message) {
        super(message);
    }
}
