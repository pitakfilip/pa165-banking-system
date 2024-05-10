package cz.muni.pa165.banking.security;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    private static final String SECURITY_SCHEME_OAUTH2 = "MUNI";
    private static final String SECURITY_SCHEME_BEARER = "Bearer";

    /**
     * Add security definitions to generated openapi.yaml.
     */
    @Bean
    public OpenApiCustomizer openAPICustomizer() {
        return openApi -> {
            openApi.getComponents()
                    .addSecuritySchemes("Authorization", new SecurityScheme()
                            .type(SecurityScheme.Type.APIKEY)
                            .in(SecurityScheme.In.HEADER)
                            .name("Bearer Authorization Token")
                    )
            ;
            openApi.addSecurityItem(new SecurityRequirement().addList("Bearer Authorization Token"));
        };
    }


}
