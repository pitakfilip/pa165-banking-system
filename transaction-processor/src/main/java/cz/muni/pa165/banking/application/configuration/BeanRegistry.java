package cz.muni.pa165.banking.application.configuration;

import cz.muni.pa165.banking.exception.CustomExceptionHandler;
import cz.muni.pa165.banking.security.BaseSecurityFilter;
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
    BaseSecurityFilter securityFilter() {
        return new BaseSecurityFilter();
    }
    
    @Bean
    SwaggerConfiguration swaggerConfiguration() {
        return new SwaggerConfiguration();
    }
    
}
