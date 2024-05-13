package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteAccountSuspendedException extends Exception{
    public RemoteAccountSuspendedException(String message) {
        super(message);
    }
}
