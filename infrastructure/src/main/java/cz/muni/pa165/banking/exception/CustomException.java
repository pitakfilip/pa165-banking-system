package cz.muni.pa165.banking.exception;

import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CustomException extends RuntimeException {

    public final Map<String, Object> getBody() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", getStatus());
        body.put("code", getStatus().value());
        body.put("exception", this.getClass().getSimpleName());
        body.put("message", getExceptionMessage());
        body.put("cause", getExceptionCause());
        
        if (getDetail() != null) {
            body.put("detail", getDetail());
        }
        
        return body;
    }
    
    public abstract HttpStatus getStatus();
    
    public abstract String getExceptionMessage();

    abstract String getExceptionCause();
    
    abstract Object getDetail();
    
}
