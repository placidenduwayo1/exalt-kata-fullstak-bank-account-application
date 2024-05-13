package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteCustomerApiUnreachableException extends Exception{
    public RemoteCustomerApiUnreachableException(String message) {
        super(message);
    }
}
