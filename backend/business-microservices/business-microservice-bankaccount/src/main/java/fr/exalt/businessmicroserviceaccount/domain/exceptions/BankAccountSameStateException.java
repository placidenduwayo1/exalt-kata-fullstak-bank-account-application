package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountSameStateException extends Exception{
    public BankAccountSameStateException(String message) {
        super(message);
    }
}
