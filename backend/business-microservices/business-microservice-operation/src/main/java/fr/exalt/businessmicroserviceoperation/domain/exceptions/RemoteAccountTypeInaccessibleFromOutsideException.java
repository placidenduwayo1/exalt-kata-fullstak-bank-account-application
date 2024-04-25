package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteAccountTypeInaccessibleFromOutsideException extends Exception{
    public RemoteAccountTypeInaccessibleFromOutsideException(String message) {
        super(message);
    }
}
