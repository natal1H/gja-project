package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.*;
import fit.gja.songtrainer.service.*;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;


/**
 * Controller class responsible for handling request for playlists page.
 */
@Controller
public class PlaylistController {

    private final PlaylistService playlistService;

    private final SongService songService;

    private final UserService userService;

    /**
     * Class constructor, injects the necessary services
     * @param playlistService Service handling database request about playlists
     * @param songService Service handling database request about songs
     * @param userService Service handling database request about users
     */
    public PlaylistController(PlaylistService playlistService, SongService songService, UserService userService) {
        this.playlistService = playlistService;
        this.songService = songService;
        this.userService = userService;
    }

    /**
     * Controller method responsible for mapping "/playlists"
     * @param theModel holder of attributes
     * @return playlists page filename
     */
    // TODO rename to showPlaylists
    @GetMapping("/playlists")
    @Transactional
    public String showHome(Model theModel) {
        // Get list of user's playlists
        User user = UserUtil.getCurrentUser(userService);

        // add the songs to the model
        theModel.addAttribute("playlists", user.getPlaylists());
        theModel.addAttribute("user", user);

        return "playlists";
    }

    /**
     * Controller method responsible for mapping "/playlist"
     * @param playlistId id of playlists to display
     * @param sortStr sorting option
     * @return model with attributes and selected view
     */
    @RequestMapping(value = "/playlist", method = RequestMethod.GET)
    public ModelAndView listAllSongsInPlaylist(@RequestParam("id") Long playlistId, @RequestParam("sort") String sortStr) {
        ModelAndView mav = new ModelAndView();

        User user = UserUtil.getCurrentUser(userService);

        // Load playlist based on id
        Playlist thePlaylist = playlistService.getPlaylistById(playlistId);
        // Verify playlist exists - if not redirect to access denied
        if (thePlaylist == null) {
            mav.setViewName("access-denied");
            return mav;
        }

        // Check if playlist belongs to current user
        if (!Objects.equals(thePlaylist.getUser().getId(), user.getId())) {
            mav.setViewName("access-denied");
            return mav;
        }

        List<Song> theSongs = playlistService.getSortedPlaylistsSongsByOption(thePlaylist, sortStr);

        // Add playlist and songs to model
        mav.addObject("playlist", thePlaylist);
        mav.addObject("songs", theSongs);

        mav.setViewName("playlist");

        return mav;
    }

    /**
     * Controller method responsible for mapping "/playlist/addPlaylist"
     * @param theModel holder of attributes
     * @return add playlist form filename
     */
    @GetMapping("/playlist/addPlaylist")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Playlist thePlaylist = new Playlist();

        theModel.addAttribute("playlist", thePlaylist);
        theModel.addAttribute("instruments", InstrumentEnum.values());

        return "playlist-form";
    }

    /**
     * Controller method responsible for mapping "/playlist/savePlaylist"
     * @param thePlaylist the playlist which to save
     * @return redirects to saved playlist
     */
    // TODO - add form validations
    // TODO - remove logic from controller and do it in service
    @PostMapping("/playlist/savePlaylist")
    public String saveSong(@ModelAttribute("playlist") Playlist thePlaylist) {
        // set user
        User user = UserUtil.getCurrentUser(userService);
        thePlaylist.setUser(user);

        // try to see if playlist already exists:
        if (thePlaylist.getId() != null) {
            // Find original playlist in db
            Playlist originalPlaylist = playlistService.getPlaylistById(thePlaylist.getId());

            // if different instrument now, remove song from playlists
            if (originalPlaylist.getInstrument() != thePlaylist.getInstrument()) {
                for (Song tempSong : originalPlaylist.getSongs()) { // remove playlist from all songs it had
                    songService.deletePlaylistFromSong(tempSong, originalPlaylist);
                }
                List<Song> allSongs = originalPlaylist.getSongs();
                originalPlaylist.getSongs().removeAll(allSongs);
            }
            originalPlaylist.setName(thePlaylist.getName());
            originalPlaylist.setInstrument(thePlaylist.getInstrument());

            playlistService.save(originalPlaylist);
        } else {
            playlistService.save(thePlaylist);
        }


        return "redirect:/playlist?id=" + thePlaylist.getId() + "&sort=ArtistASC";
    }

    /**
     * Controller method responsible for mapping "/playlist/deleteSong"
     * @param theSongId id of song to delete
     * @param thePlaylistId id of playlist
     * @return redirects back to the playlist
     */
    @GetMapping("/playlist/deleteSong")
    public String deleteSong(@RequestParam("songId") Long theSongId, @RequestParam("playlistId") Long thePlaylistId) {
        // delete the song
        songService.delete(theSongId);

        return "redirect:/playlist?id=" + thePlaylistId + "&sort=ArtistASC";
    }

    /**
     * Controller method responsible for mapping "/playlist/deletePlaylist"
     * @param thePlaylistId id of playlist which to delete
     * @return redirects back to the home page
     */
    @GetMapping("/playlist/deletePlaylist")
    public String deletePlaylist(@RequestParam("playlistId") Long thePlaylistId) {
        // delete the playlist
        playlistService.delete(thePlaylistId);

        return "redirect:/";
    }

    /**
     * Controller method responsible for mapping "/playlist/showUpdateForm"
     * @param theId id of playlist which to update
     * @param theModel holder of attributes
     * @return update playlist form filename
     */
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

    /**
     * Controller method responsible for mapping "/playlist/removeSongFromPlaylist"
     * @param theSongId id of song to remove
     * @param thePlaylistId id of playlist from which remove song
     * @return redirects back to the playlist
     */
    @GetMapping("/playlist/removeSongFromPlaylist")
    public String removeSongFromPlaylist(@RequestParam("songId") Long theSongId, @RequestParam("playlistId") Long thePlaylistId) {
        Playlist thePlaylist = playlistService.getPlaylistById(thePlaylistId);
        Song theSong = songService.getSongById(theSongId);

        playlistService.deleteSongFromPlaylist(thePlaylist, theSong);

        return "redirect:/playlist?id=" + thePlaylistId + "&sort=ArtistASC";
    }
}
