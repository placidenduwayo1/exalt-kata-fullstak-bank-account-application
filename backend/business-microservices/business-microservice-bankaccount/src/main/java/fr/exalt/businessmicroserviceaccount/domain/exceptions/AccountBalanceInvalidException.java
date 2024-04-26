package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class AccountBalanceInvalidException extends Exception{
    public AccountBalanceInvalidException(String message) {
        super(message);
    }
}
