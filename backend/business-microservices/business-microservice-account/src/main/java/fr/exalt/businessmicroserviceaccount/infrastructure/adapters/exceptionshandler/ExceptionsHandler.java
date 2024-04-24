package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.exceptionshandler;

import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleBusinessExceptions(Exception exception) {

        return switch (exception) {
            case AccountBalanceInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case AccountFieldsInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case AccountTypeInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteCustomerApiUnreachableException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteCustomerStateInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

            default -> new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        };
    }
}
