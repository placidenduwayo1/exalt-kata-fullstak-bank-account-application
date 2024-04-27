package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountBalanceInvalidException extends Exception{
    public BankAccountBalanceInvalidException(String message) {
        super(message);
    }
}
