package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteBankAccountApiUnreachableException extends Exception{
    public RemoteBankAccountApiUnreachableException(String message) {
        super(message);
    }
}
