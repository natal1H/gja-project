package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.*;
import fit.gja.songtrainer.service.PlaylistService;
import fit.gja.songtrainer.service.SongService;
import fit.gja.songtrainer.service.UserService;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
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
    private SongService songService;

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

    @GetMapping("/playlist/addPlaylist")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Playlist thePlaylist = new Playlist();

        theModel.addAttribute("playlist", thePlaylist);
        theModel.addAttribute("instruments", InstrumentEnum.values());

        return "playlist-form";
    }

    // TODO - add form validations
    @PostMapping("/playlist/savePlaylist")
    public String saveSong(@ModelAttribute("playlist") Playlist thePlaylist) {

        // set user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByUserName(userDetail.getUsername());
        thePlaylist.setUser(u);

        // save the customer using our service
        playlistService.save(thePlaylist);

        return "redirect:/playlist?id=" + thePlaylist.getId();
    }

    @GetMapping("/playlist/deleteSong")
    public String deleteSong(@RequestParam("songId") Long theSongId, @RequestParam("playlistId") Long thePlaylistId) {
        // delete the song
        songService.delete(theSongId);

        return "redirect:/playlist?id=" + thePlaylistId;
    }

    @GetMapping("/playlist/deletePlaylist")
    public String deletePlaylist(@RequestParam("playlistId") Long thePlaylistId) {
        // delete the playlist
        playlistService.delete(thePlaylistId);

        return "redirect:/";
    }

    @GetMapping("/playlist/showUpdateForm")
    public String showUpdateForm(@RequestParam("playlistId") Long theId, Model theModel) {
        // get song from database
        Playlist thePlaylist = playlistService.getPlaylistById(theId);

        // set song as a model attribute to pre-populate the form
        theModel.addAttribute("playlist", thePlaylist);
        theModel.addAttribute("instruments", InstrumentEnum.values());

        // send over to the form
        return "playlist-form";
    }

    @GetMapping("/playlist/removeSongFromPlaylist")
    public String removeSongFromPlaylist(@RequestParam("songId") Long theSongId, @RequestParam("playlistId") Long thePlaylistId) {
        Playlist thePlaylist = playlistService.getPlaylistById(thePlaylistId);
        Song theSong = songService.getSongById(theSongId);

        playlistService.deleteSongFromPlaylist(thePlaylist, theSong);
        songService.deletePlaylistFromSong(theSong, thePlaylist);

        return "redirect:/playlist?id=" + thePlaylistId;
    }
}
