package cz.muni.pa165.banking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default configuration for Spring Security using OAuth2.
 */
@Component
public class BaseSecurityFilter {
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(customizer ->
                        customizer
                                .requestMatchers( evalUnauthorizedPaths() ).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(configurer ->
                        configurer.opaqueToken(Customizer.withDefaults())
                );
        return httpSecurity.build();
    }


    private final List<String> DEFAULT_UNAUTHORIZED_PATHS = List.of(
            "/actuator/**",
            "/swagger-ui/**",
            "/v3/**",
            "/openapi/**"
    );

    private String[] evalUnauthorizedPaths() {
        List<String> merged = new ArrayList<>(DEFAULT_UNAUTHORIZED_PATHS);
        merged.addAll(Arrays.asList(unauthorizedPaths()));
        return merged.toArray(String[]::new);
    }

    /**
     * For extending class in case of need to add more unauthorized paths to exclude from spring security 
     */
    protected String[] unauthorizedPaths() {
        return new String[0];
    }

}
