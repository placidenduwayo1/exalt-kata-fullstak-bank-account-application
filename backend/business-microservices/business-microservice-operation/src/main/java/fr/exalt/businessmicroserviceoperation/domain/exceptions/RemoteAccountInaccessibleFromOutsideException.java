package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteAccountInaccessibleFromOutsideException extends Exception{
    public RemoteAccountInaccessibleFromOutsideException(String message) {
        super(message);
    }
}
