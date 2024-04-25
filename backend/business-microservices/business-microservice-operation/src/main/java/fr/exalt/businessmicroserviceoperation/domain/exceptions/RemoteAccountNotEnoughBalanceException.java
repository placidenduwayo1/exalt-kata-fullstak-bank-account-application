package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteAccountNotEnoughBalanceException extends Exception{
    public RemoteAccountNotEnoughBalanceException(String message) {
        super(message);
    }
}
