package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteBankAccountTypeInaccessibleFromOutsideException extends Exception{
    public RemoteBankAccountTypeInaccessibleFromOutsideException(String message) {
        super(message);
    }
}
