package cz.muni.pa165.banking.client;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.io.IOException;

@SpringBootApplication
public class ClientApplication {

    private static final Logger log = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    /**
     * Configuration of Spring Security. Sets up OAuth2/OIDC authentication
     * for all URLS except a list of public ones.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(x -> x
                        .requestMatchers("/", "/error", "/robots.txt", "/style.css", "/favicon.ico", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(x -> x
                        .successHandler(authenticationSuccessHandler())
                )
                .logout(x -> x
                         .logoutSuccessUrl("/")
                        .logoutSuccessHandler(oidcLogoutSuccessHandler())
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
        ;
        return httpSecurity.build();
    }

    /**
     * Handler called when OIDC login successfully completes.
     * It extends the default SavedRequestAwareAuthenticationSuccessHandler that saves the access token
     * to the session.
     * This handler just prints the available info about user to the log and calls its parent implementation.
     * @see SavedRequestAwareAuthenticationSuccessHandler
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws ServletException, IOException {
                if (auth instanceof OAuth2AuthenticationToken token
                        && token.getPrincipal() instanceof OidcUser user) {
                    log.debug("********************************************************");
                    log.debug("* user successfully logged in                          *");
                    log.debug("********************************************************");
                    log.info("user.issuer: {}", user.getIssuer());
                    log.info("user.subject: {}", user.getSubject());
                    log.info("user.fullName: {}", user.getFullName());
                    log.info("user.givenName: {}", user.getGivenName());
                    log.info("user.familyName: {}", user.getFamilyName());
                    log.info("user.gender: {}", user.getGender());
                    log.info("user.email: {}", user.getEmail());
                    log.info("user.locale: {}", user.getLocale());
                    log.info("user.zoneInfo: {}", user.getZoneInfo());
                    log.info("user.preferredUsername: {}", user.getPreferredUsername());
                    log.info("user.issuedAt: {}", user.getIssuedAt());
                    log.info("user.authenticatedAt: {}", user.getAuthenticatedAt());
                    log.info("user.claimAsListString(\"eduperson_scoped_affiliation\"): {}", user.getClaimAsStringList("eduperson_scoped_affiliation"));
                    log.info("user.attributes.acr: {}", user.<String>getAttribute("acr"));
                    log.info("user.attributes: {}", user.getAttributes());
                    log.info("user.authorities: {}", user.getAuthorities());
                }
                super.onAuthenticationSuccess(req, res, auth);
            }
        };
    }


    /**
     * Handler called when local logout successfully completes.
     * It initiates also a complete remote logout at the Authorization Server.
     * @see OidcClientInitiatedLogoutSuccessHandler
     */
    private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler successHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("http://localhost:8084/");
        return successHandler;
    }

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

}
