package fit.gja.songtrainer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class responsible for handling request for login page.
 */
@Controller
public class LoginController {

    /**
     * Controller method responsible for mapping "/showLoginPage".
     * @return login page filename
     */
    @GetMapping("/showLoginPage")
    public String showLoginPage() {
        return "login";
    }

    /**
     * Controller method responsible for mapping "/access-denied".
     * @return access denied page filename
     */
    // add request mapping for /access-denied
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
