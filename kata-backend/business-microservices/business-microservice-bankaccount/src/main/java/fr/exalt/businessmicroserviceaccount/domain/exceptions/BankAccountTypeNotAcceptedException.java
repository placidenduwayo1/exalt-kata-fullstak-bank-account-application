package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountTypeNotAcceptedException extends Exception{
    public BankAccountTypeNotAcceptedException(String message) {
        super(message);
    }
}
