package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.UserService;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.util.SongsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;


@Controller
public class ProfileController {

    private final UserService userService;


    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    // TODO - remove logic from controller
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView showProfile(@RequestParam("id") String userIdStr, @RequestParam("inst") String instrumentStr, @RequestParam("sort") String sortStr) {
        ModelAndView mav = new ModelAndView();

        // get requested user
        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            mav.setViewName("access-denied");
            return mav;
        }

        User user = userService.getUserById(userId);
        if (user == null || !InstrumentEnum.isValidStr(instrumentStr) && !instrumentStr.equals("ALL")) {
            mav.setViewName("access-denied");
            return mav;
        }

        // Get user's songs
        List<Song> theSongs = user.getSongs();
        theSongs = SongsUtil.filterSongsByVisible(theSongs); // Remove those set to invisible
        if (!Objects.equals(instrumentStr, "ALL")) {
            theSongs = SongsUtil.filterSongsByInstrument(theSongs, instrumentStr);
        }
        theSongs = SongsUtil.sortSongS(theSongs, sortStr); // sort songs

        // add attributes to the model
        mav.addObject("user", user);
        mav.addObject("songs", theSongs);

        mav.setViewName("profile");

        return mav;
    }
}
