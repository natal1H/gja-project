package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.*;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.util.SongsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import fit.gja.songtrainer.util.UserUtil;

import java.util.List;
import java.util.Objects;


/**
 * Controller class responsible for handling request from user profile page.
 */
@Controller
public class ProfileController {

    private final UserService userService;
    private final RoleService roleService;


    /**
     * Class constructor, injects the necessary services
     * @param roleService Service handling database request about roles
     * @param userService Service handling database request about users
     */
    public ProfileController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    /**
     * Controller method responsible for mapping "/profile".
     * @param userIdStr id of user whose profile to show
     * @param instrumentStr instrument option string
     * @param sortStr sorting option
     * @return model with added attributes and selected view
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView showProfile(@RequestParam("id") String userIdStr, @RequestParam("inst") String instrumentStr, @RequestParam("sort") String sortStr) {
        ModelAndView mav = new ModelAndView();

        // get logged in user
        User user = UserUtil.getCurrentUser(userService);

        // get requested user
        Long profileUserId;
        try {
            profileUserId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            mav.setViewName("access-denied");
            return mav;
        }

        User profileUser = userService.getUserById(profileUserId);
        if (profileUser == null || !InstrumentEnum.isValidStr(instrumentStr) && !instrumentStr.equals("ALL")) {
            mav.setViewName("access-denied");
            return mav;
        }

        // Get user's songs
        List<Song> theSongs = profileUser.getSongs();
        theSongs = SongsUtil.filterSongsByVisible(theSongs); // Remove those set to invisible
        if (!Objects.equals(instrumentStr, "ALL")) {
            theSongs = SongsUtil.filterSongsByInstrument(theSongs, instrumentStr);
        }
        theSongs = SongsUtil.sortSongS(theSongs, sortStr); // sort songs

        // check if user is lector
        boolean isLector = profileUser.getRoles().contains(roleService.findRoleByName("ROLE_LECTOR"));

        // add attributes to the model
        mav.addObject("user", user);
        mav.addObject("profileUser", profileUser);
        mav.addObject("songs", theSongs);
        mav.addObject("isLector", isLector);

        mav.setViewName("profile");

        return mav;
    }
}
