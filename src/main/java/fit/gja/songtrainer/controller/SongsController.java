package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.*;
import fit.gja.songtrainer.service.SongService;
import fit.gja.songtrainer.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class SongsController {

    @Autowired
    private SongService songService;
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

    @GetMapping("/addSong")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Song theSong = new Song();

        theModel.addAttribute("song", theSong);
        theModel.addAttribute("instruments", Instrument.values());
        theModel.addAttribute("tunings", Tuning.values());

        return "add-song-form";
    }

    // TODO - add form validations
    @PostMapping("/saveSong")
    public String saveSong(@ModelAttribute("song") Song theSong) {

        // set user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByUserName(userDetail.getUsername());
        theSong.setUser(u);

        // Tuning - if instrument other the guitar or bass set to none
        if (theSong.getInstrument() != Instrument.GUITAR && theSong.getInstrument() != Instrument.BASS)
            theSong.setTuning(Tuning.NONE);

        // save the customer using our service
        songService.save(theSong);

        return "home"; // TODO change to redirect to songs
    }
}
