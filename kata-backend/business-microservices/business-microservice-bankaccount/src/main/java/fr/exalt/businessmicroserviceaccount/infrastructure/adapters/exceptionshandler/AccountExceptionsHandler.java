package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.exceptionshandler;

import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleBusinessExceptions(Exception exception) {

       return switch (exception) {
            case BankAccountBalanceInvalidException e ->
                   new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case BankAccountFieldsInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case BankAccountTypeInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case BankAccountStateInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteCustomerApiUnreachableException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case RemoteCustomerStateInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case BankAccountNotFoundException e ->
                new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
            case BankAccountSameStateException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case BankAccountTypeNotAcceptedException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case BankAccountOverdraftInvalidException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case BankAccountSuspendException e ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

            default -> new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        };
    }
}
