package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class RemoteCustomerApiUnreachableException extends Exception{
    public RemoteCustomerApiUnreachableException(String message) {
        super(message);
    }
}
