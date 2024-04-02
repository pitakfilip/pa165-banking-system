package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.application.service.NotFoundAccountException;
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
        return new ResponseEntity<>(((MethodArgumentNotValidException) e).getBody(), HttpStatus.BAD_REQUEST);
    }
}
