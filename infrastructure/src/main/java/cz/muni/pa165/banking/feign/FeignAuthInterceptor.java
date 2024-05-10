package cz.muni.pa165.banking.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        OAuth2AccessToken accessToken = ((BearerTokenAuthentication) SecurityContextHolder.getContext().getAuthentication()).getToken();
        template.header("Authorization", "Bearer " + accessToken.getTokenValue());
    }
    
}
