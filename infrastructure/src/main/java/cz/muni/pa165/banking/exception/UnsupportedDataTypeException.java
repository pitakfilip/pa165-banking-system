package cz.muni.pa165.banking.exception;

import org.springframework.http.HttpStatus;

public class UnsupportedDataTypeException extends CustomException {
    
    private final String cause;

    public UnsupportedDataTypeException(String cause) {
        this.cause = cause;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    String getExceptionMessage() {
        return "Unsupported data type";
    }

    @Override
    String getExceptionCause() {
        return cause;
    }

    @Override
    Object getDetail() {
        return null;
    }
}
