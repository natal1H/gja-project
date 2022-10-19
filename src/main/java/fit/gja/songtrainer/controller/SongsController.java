package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.dao.SongDao;
import fit.gja.songtrainer.entity.Instrument;
import fit.gja.songtrainer.entity.InstrumentConverter;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class SongsController {

    @Autowired
    private SongDao songDao;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/songs", method = RequestMethod.GET)
    public ModelAndView listSongs(@RequestParam("inst") String instrumentStr) {
        ModelAndView mav = new ModelAndView();

        // get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByUserName(userDetail.getUsername());

        // depending on GET param choose which type of songs to get
        InstrumentConverter instConv = new InstrumentConverter();
        List<Song> theSongs = null;
        if (instrumentStr.equals("all")) {
            theSongs = u.getSongs();
        }
        else if (instConv.isValidString(instrumentStr)) {
            theSongs = u.getSongs();
            // creating a Predicate condition checking for non instrument songs
            Predicate<Song> isNotGuitar = item -> item.getInstrument() == instConv.convertToEntityAttribute(instrumentStr);
            theSongs = theSongs.stream().filter(isNotGuitar).collect(Collectors.toList());
        }
        else { // bad param - redirect to access-denied
            mav.setViewName("access-denied");
            return mav;
        }

        // add the songs to the model
        mav.addObject("songs", theSongs);

        mav.setViewName("songs");

        return mav;
    }
}
