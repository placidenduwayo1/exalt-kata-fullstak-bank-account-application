package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input;

import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleBusinessException (Exception exception){
       if(exception instanceof CustomerNotFoundException){
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
       } else if (exception instanceof CustomerOneOrMoreFieldsInvalidException) {
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
       } else if (exception instanceof CustomerStateInvalidException) {
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
       } else if (exception instanceof CustomerAlreadyExistsException) {
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
       } else if (exception instanceof AddressNotFoundException) {
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
       } else {
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
       }
    }
}
