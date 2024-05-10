package cz.muni.pa165.banking.feign;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {
    
    @Bean
    RequestInterceptor tokenPropagationInterceptor() {
        return new FeignAuthInterceptor();
    }
    
    @Bean
    FeignClientLogger clientLogger() {
        return new FeignClientLogger();
    }
    
    @Bean
    Logger.Level loggingLevel() {
        return Logger.Level.FULL;
    }
    
}
