package cz.muni.pa165.banking.application.configuration;

import cz.muni.pa165.banking.exception.CustomExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanRegistry {
    
    @Bean
    public CustomExceptionHandler exceptionHandler() {
        return new CustomExceptionHandler();
    }
    
}
