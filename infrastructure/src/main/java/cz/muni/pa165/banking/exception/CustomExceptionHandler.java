package cz.muni.pa165.banking.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles custom exceptions thrown in application. The handler overrides the controller return value,
 * evaluating the assigned status code and response body. 
 * The response body is evaluated as a JSON object, containing defined information such as the status code, 
 * exception type, message, cause and an optional detail regarding the exception.
 */
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleEntityNotFound(CustomException ex) {
        return new ResponseEntity<>(ex.getBody(), ex.getStatus());
    }
    
}
