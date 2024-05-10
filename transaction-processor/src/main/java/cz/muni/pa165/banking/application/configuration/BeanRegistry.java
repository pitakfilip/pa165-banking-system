package cz.muni.pa165.banking.application.configuration;

import cz.muni.pa165.banking.exception.CustomExceptionHandler;
import cz.muni.pa165.banking.security.BaseSecurityFilterConfiguration;
import cz.muni.pa165.banking.security.SwaggerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class BeanRegistry {
    
    @Bean
    public CustomExceptionHandler exceptionHandler() {
        return new CustomExceptionHandler();
    }
    
    @Bean
    BaseSecurityFilterConfiguration securityFilter() {
        return new BaseSecurityFilterConfiguration();
    }
    
    @Bean
    SwaggerConfiguration swaggerConfiguration() {
        return new SwaggerConfiguration();
    }
    
}
