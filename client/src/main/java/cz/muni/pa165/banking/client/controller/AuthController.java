package cz.muni.pa165.banking.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZoneId;
import java.util.TreeSet;

/**
 * Spring MVC Controller.
 * Handles HTTP requests by preparing data in model and passing it to Thymeleaf HTML templates.
 */
@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private OAuth2AuthorizedClientService clientService;
    
    /**
     * Home page accessible even to non-authenticated users. Displays user personal data.
     */
    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal OidcUser user) {
        log.debug("********************************************************");
        log.debug("* index() called                                       *");
        log.debug("********************************************************");
        log.debug("user {}", user == null ? "is anonymous" : user.getSubject());
        model.addAttribute("user", user);

        if (user != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
            log.info("BEARER: " + client.getAccessToken().getTokenValue());
            
            model.addAttribute("issuerName",
                    "https://oidc.muni.cz/oidc/".equals(user.getIssuer().toString()) ? "MUNI" : "Google");
            model.addAttribute("token", client.getAccessToken().getTokenValue());
        }

        return "index";
    }

}