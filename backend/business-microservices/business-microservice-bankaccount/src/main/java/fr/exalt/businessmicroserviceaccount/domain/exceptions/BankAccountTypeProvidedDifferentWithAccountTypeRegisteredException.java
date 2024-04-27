package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountTypeProvidedDifferentWithAccountTypeRegisteredException extends Exception{
    public BankAccountTypeProvidedDifferentWithAccountTypeRegisteredException(String message) {
        super(message);
    }
}
