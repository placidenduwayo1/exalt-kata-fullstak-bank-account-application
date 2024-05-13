package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class OperationTypeInvalidException extends Exception{
    public OperationTypeInvalidException(String message) {
        super(message);
    }
}
