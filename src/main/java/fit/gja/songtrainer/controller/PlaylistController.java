package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.*;
import fit.gja.songtrainer.service.PlaylistService;
import fit.gja.songtrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;


@Controller
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/playlist", method = RequestMethod.GET)
    public ModelAndView listAllSongsInPlaylist(@RequestParam("id") Long playlistId) {
        ModelAndView mav = new ModelAndView();

        // Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByUserName(userDetail.getUsername());

        // Load playlist based on id
        Playlist thePlaylist = playlistService.getPlaylistById(playlistId);
        // Verify playlist exists - if not redirect to access denied
        if (thePlaylist == null) {
            mav.setViewName("access-denied");
            return mav;
        }

        // Check if playlist belongs to current user
        if (!Objects.equals(thePlaylist.getUser().getId(), u.getId())) {
            mav.setViewName("access-denied");
            return mav;
        }

        // Add playlist and songs to model
        mav.addObject("playlist", thePlaylist);
        mav.addObject("songs", thePlaylist.getSongs());

        mav.setViewName("playlist");

        return mav;
    }

    @GetMapping("/addPlaylist")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Playlist thePlaylist = new Playlist();

        theModel.addAttribute("playlist", thePlaylist);
        theModel.addAttribute("instruments", Instrument.values());

        return "add-playlist-form";
    }

    // TODO - add form validations
    @PostMapping("/savePlaylist")
    public String saveSong(@ModelAttribute("playlist") Playlist thePlaylist) {

        // set user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByUserName(userDetail.getUsername());
        thePlaylist.setUser(u);

        // save the customer using our service
        playlistService.save(thePlaylist);

        return "redirect:/"; // TODO - redirect to new playlist
    }
}
