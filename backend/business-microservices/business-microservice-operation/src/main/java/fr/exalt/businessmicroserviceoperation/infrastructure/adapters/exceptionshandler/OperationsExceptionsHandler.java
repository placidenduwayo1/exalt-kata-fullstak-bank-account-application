package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.exceptionshandler;

import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
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
            case RemoteBankAccountApiUnreachableException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteBankAccountBalanceException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteBankAccountTypeInaccessibleFromOutsideException e ->
                new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteCustomerStateInvalidException e ->
                new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteCustomerApiUnreachableException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            default ->
                    new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        };
    }
}
