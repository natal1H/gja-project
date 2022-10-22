package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.*;
import fit.gja.songtrainer.service.SongService;
import fit.gja.songtrainer.service.UserService;

import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.util.Tuning.TuningEnum;
import org.hibernate.LazyInitializationException;
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
        List<Song> theSongs = null;
        if (instrumentStr.equals("ALL")) {
            theSongs = u.getSongs();
        }
        else if (InstrumentEnum.isValidStr(instrumentStr)) {
            theSongs = u.getSongs();
            // creating a Predicate condition checking for non instrument songs
            Predicate<Song> isNotGuitar = item -> item.getInstrument() == InstrumentEnum.valueOf(instrumentStr);
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

    @GetMapping("/songs/addSong")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Song theSong = new Song();

        theModel.addAttribute("song", theSong);
        theModel.addAttribute("instruments", InstrumentEnum.values());
        theModel.addAttribute("tunings", TuningEnum.values());

        return "song-form";
    }

    // TODO - add form validations
    @PostMapping("/songs/saveSong")
    public String  saveSong(@ModelAttribute("song") Song theSong) {

        // set user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = null;

        // the exception caused crash - fix it if you dare
        try {
            u = userService.findByUserName(userDetail.getUsername());
        }
        catch (org.hibernate.LazyInitializationException ignored) {}
        finally {
            theSong.setUser(u);
        }

        // Tuning - if instrument other the guitar or bass set to none
        if (theSong.getInstrument() != InstrumentEnum.GUITAR && theSong.getInstrument() != InstrumentEnum.BASS)
            theSong.setTuning(TuningEnum.NONE);

        // save the customer using our service
        songService.save(theSong);

        return "redirect:/songs?inst=ALL";
    }

    @GetMapping("/songs/delete")
    public String deleteSong(@RequestParam("songId") Long theId) {
        // delete the song
        songService.delete(theId);

        return "redirect:/songs?inst=ALL";
    }

    @GetMapping("/songs/showUpdateForm")
    public String showUpdateForm(@RequestParam("songId") Long theId, Model theModel) {
        // get song from database
        Song theSong = songService.getSongById(theId);

        // set song as a model attribute to pre-populate the form
        theModel.addAttribute("song", theSong);
        theModel.addAttribute("instruments", InstrumentEnum.values());
        theModel.addAttribute("tunings", TuningEnum.values());

        // send over to the form
        return "song-form";
    }
}
