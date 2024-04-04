package cz.muni.pa165.banking.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Martin Mojzis
 */
@ControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(NotFoundAccountException.class)
    public ResponseEntity<Object> handleNotFoundAccount(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
