package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteAccountApiUnreachableException extends Exception{
    public RemoteAccountApiUnreachableException(String message) {
        super(message);
    }
}
