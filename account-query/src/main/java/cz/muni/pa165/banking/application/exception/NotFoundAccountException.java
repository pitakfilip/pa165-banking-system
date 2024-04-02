package cz.muni.pa165.banking.application.exception;

/**
 * @author Martin Mojzis
 */
public class NotFoundAccountException extends RuntimeException{
    public NotFoundAccountException(String message) {
        super(message);
    }

}
