package cz.muni.pa165.banking.exception;

import org.springframework.http.HttpStatus;

public class ServerError extends CustomException {
    
    private final String cause;

    private final Object detail;

    public ServerError(String cause) {
        this.cause = cause;
        detail = null;
    }

    public ServerError(String cause, Object detail) {
        this.cause = cause;
        this.detail = detail;
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    String getExceptionMessage() {
        return "Internal Server Error";
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
