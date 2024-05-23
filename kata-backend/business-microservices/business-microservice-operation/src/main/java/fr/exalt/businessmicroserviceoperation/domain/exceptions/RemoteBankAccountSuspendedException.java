package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteBankAccountSuspendedException extends Exception{
    public RemoteBankAccountSuspendedException(String message) {
        super(message);
    }
}
