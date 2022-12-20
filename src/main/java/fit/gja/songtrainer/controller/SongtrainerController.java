package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.UserService;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class responsible for request mappings coming from the home page.
 */
@Controller
public class SongtrainerController {

    @Autowired
    private UserService userService;

    /**
     * Controller method responsible for mapping "/"
     * @param theModel holder of attributes
     * @return filename of .jsp that should be used for "/"
     */
    @GetMapping("/")
    @Transactional
    public String showHome(Model theModel) {
        // Get list of user's playlists
        User user = UserUtil.getCurrentUser(userService);

        // add the songs to the model
        theModel.addAttribute("playlists", user.getPlaylists());
        theModel.addAttribute("user", user);

        return "home";
    }

    /**
     * Controller method responsible for mapping "/lectors".
     * This mapping is only accessible for users with ROLE_LECTOR.
     * @return filename of .jsp that should be used for "/lectors"
     */
    @GetMapping("/lectors")
    public String showLectors() {
        return "lectors";
    }
}
