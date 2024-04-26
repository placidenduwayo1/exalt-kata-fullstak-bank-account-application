package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class AccountTypeProvidedDifferentWithAccountTypeRegisteredException extends Exception{
    public AccountTypeProvidedDifferentWithAccountTypeRegisteredException(String message) {
        super(message);
    }
}
