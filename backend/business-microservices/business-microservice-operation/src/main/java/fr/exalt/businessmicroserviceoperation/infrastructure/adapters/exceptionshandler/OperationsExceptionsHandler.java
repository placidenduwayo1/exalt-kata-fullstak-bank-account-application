package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.exceptionshandler;

import fr.exalt.businessmicroserviceoperation.domain.exceptions.OperationRequestFieldsInvalidException;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.OperationTypeInvalidException;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.RemoteAccountNotEnoughBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OperationsExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleBusinessExceptions(Exception exception){
        return switch (exception) {
            case OperationRequestFieldsInvalidException e ->
                new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case OperationTypeInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteAccountApiUnreachableException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteAccountNotEnoughBalanceException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            default ->
                    new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        };
    }
}
