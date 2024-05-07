package cz.muni.pa165.banking.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Spring MVC Controller.
 * Handles HTTP requests by preparing data in model and passing it to Thymeleaf HTML templates.
 */
@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

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
            model.addAttribute("issuerName",
                    "https://oidc.muni.cz/oidc/".equals(user.getIssuer().toString()) ? "MUNI" : "Google");
            model.addAttribute("token", user.getIdToken().getTokenValue());
        }

        return "index";
    }

}