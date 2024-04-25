package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class OperationRequestFieldsInvalidException extends Exception{
    public OperationRequestFieldsInvalidException(String message) {
        super(message);
    }
}
