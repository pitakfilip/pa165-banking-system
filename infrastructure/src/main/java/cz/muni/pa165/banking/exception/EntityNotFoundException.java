package cz.muni.pa165.banking.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends CustomException {
    
    private final String cause;

    private final Object detail;

    public EntityNotFoundException(String cause) {
        this.cause = cause;
        detail = null;
    }

    public EntityNotFoundException(String cause, Object detail) {
        this.cause = cause;
        this.detail = detail;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getExceptionMessage() {
        return "Entity not present in repository";
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
