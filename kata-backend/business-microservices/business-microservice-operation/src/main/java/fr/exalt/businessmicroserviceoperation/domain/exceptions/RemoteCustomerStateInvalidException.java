package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteCustomerStateInvalidException extends Exception{
    public RemoteCustomerStateInvalidException(String message) {
        super(message);
    }
}
