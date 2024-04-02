package cz.muni.pa165.banking.application.service;

/**
 * @author Martin Mojzis
 */
public class NotFoundAccountException extends Exception{
    public NotFoundAccountException(String message) {
        super(message);
    }

}
