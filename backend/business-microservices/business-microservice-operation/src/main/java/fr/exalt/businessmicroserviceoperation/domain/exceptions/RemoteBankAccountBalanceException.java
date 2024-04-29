package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteBankAccountBalanceException extends Exception{
    public RemoteBankAccountBalanceException(String message) {
        super(message);
    }
}
