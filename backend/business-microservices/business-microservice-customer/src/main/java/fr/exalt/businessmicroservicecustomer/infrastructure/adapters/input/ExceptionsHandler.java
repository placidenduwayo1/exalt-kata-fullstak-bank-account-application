package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input;

import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerOneOrMoreFieldsInvalidException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerStateInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionsHandler {
    public ResponseEntity<String> handleBusinessException (Exception exception){
       if(exception instanceof CustomerNotFoundException){
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
       } else if (exception instanceof CustomerOneOrMoreFieldsInvalidException) {
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
       } else if (exception instanceof CustomerStateInvalidException) {
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
       } else {
           return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
       }
    }
}
