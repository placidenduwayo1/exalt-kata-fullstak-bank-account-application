package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.excetionshandler;

import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleBusinessException (Exception exception){
       return  switch (exception) {
           case CustomerNotFoundException e ->
                   new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
           case CustomerOneOrMoreFieldsInvalidException e ->
                   new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
           case CustomerStateInvalidException e ->
                   new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
           case CustomerAlreadyExistsException e ->
                   new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
           case AddressNotFoundException e ->
                   new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

           default -> new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
       };
    }
}
