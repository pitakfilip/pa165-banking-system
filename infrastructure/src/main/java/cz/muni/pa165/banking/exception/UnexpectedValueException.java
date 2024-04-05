package cz.muni.pa165.banking.exception;

import org.springframework.http.HttpStatus;

public class UnexpectedValueException extends CustomException {
    
    private final String cause;
    
    private final Object detail;

    public UnexpectedValueException(String cause, Object detail) {
        this.cause = cause;
        this.detail = detail;
    }

    public UnexpectedValueException(String cause) {
        this.cause = cause;
        this.detail = null;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    String getExceptionMessage() {
        return "Unexpected value state";
    }

    @Override
    String getExceptionCause() {
        return cause;
    }

    @Override
    Object getDetail() {
        return detail;
    }

}
