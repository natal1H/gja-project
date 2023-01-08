package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import fit.gja.songtrainer.service.PlaylistService;
import fit.gja.songtrainer.service.SongService;
import fit.gja.songtrainer.service.StorageService;
import fit.gja.songtrainer.service.UserService;
import fit.gja.songtrainer.util.InstrumentEnum;
import fit.gja.songtrainer.util.SongsUtil;
import fit.gja.songtrainer.util.Tuning.TuningEnum;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Controller class responsible for handling request from "songs" page.
 */
@Controller
public class SongsController {

    private final SongService songService;

    private final PlaylistService playlistService;

    private final UserService userService;

    private final StorageService storageService;

    /**
     * Class constructor, injects the necessary services
     *
     * @param songService     Service handling database request about songs
     * @param playlistService Service handling database request about playlists
     * @param userService     Service handling database request about users
     */
    public SongsController(SongService songService, PlaylistService playlistService, UserService userService, StorageService storageService) {
        this.songService = songService;
        this.playlistService = playlistService;
        this.userService = userService;
        this.storageService = storageService;
    }


    /**
     * Controller method responsible for mapping "/songs".
     * Passes all user's songs to model for display.
     *
     * @param instrumentStr String representation of which instrument's songs to display
     * @return model and view object with added attributes and specified .jsp filename for "/songs"
     */
    @RequestMapping(value = "/songs", method = RequestMethod.GET)
    public ModelAndView listSongs(@RequestParam("inst") String instrumentStr) {
        ModelAndView mav = new ModelAndView();

        User user = UserUtil.getCurrentUser(userService);

        // depending on GET param choose which type of songs to get
        List<Song> theSongs = user.getSongs();

        if (!InstrumentEnum.isValidStr(instrumentStr) && !instrumentStr.equals("ALL")) {
            mav.setViewName("access-denied");
            return mav;
        }
        theSongs = SongsUtil.filterSongsByInstrument(theSongs, instrumentStr);

        // add the songs to the model
        mav.addObject("songs", theSongs);
        mav.addObject("user", user);

        mav.setViewName("songs");

        return mav;
    }

    /**
     * Controller method responsible for mapping "/songs/addSong".
     * Prepares data for add song form.
     *
     * @param theModel holder of attributes
     * @return filename of .jsp that should be used for "/songs/addSong"
     */
    @GetMapping("/songs/addSong")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Song theSong = new Song();
        theSong.setTuning(TuningEnum.NONE);
        theSong.setInstrument(InstrumentEnum.GUITAR);

        theModel.addAttribute("user", UserUtil.getCurrentUser(userService));
        theModel.addAttribute("song", theSong);
        theModel.addAttribute("instruments", InstrumentEnum.values());
        theModel.addAttribute("tunings", TuningEnum.values());
        theModel.addAttribute("allowedAudioExtensions", storageService.getAllowedBackingTrackExtensions());

        return "song-form";
    }

    /**
     * Controller method responsible for mapping "/songs/saveSong".
     * Takes submitted song and saves it to database.
     *
     * @param theSong submitted song
     * @return redirects back to the songs display
     */
    @PostMapping("/songs/saveSong")
    public String saveSong(@ModelAttribute("song") Song theSong, @RequestParam("backing_track") MultipartFile backingTrack) throws InvalidFileExtensionException, IOException, UnsupportedAudioFileException {

        // set user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = null;

        // the exception caused crash - fix it if you dare
        try {
            u = userService.findByUserName(userDetail.getUsername());
        } catch (org.hibernate.LazyInitializationException ignored) {
        } finally {
            theSong.setUser(u);
        }

        // Tuning - if instrument other the guitar or bass set to none
        if (theSong.getInstrument() != InstrumentEnum.GUITAR && theSong.getInstrument() != InstrumentEnum.BASS)
            theSong.setTuning(TuningEnum.NONE);

        // try to see if song already exists:
        if (theSong.getId() != null) {
            // song already exists, copy stats from db
            // Find original song in db
            Song originalSong = songService.getSongById(theSong.getId());

            // if different instrument now, remove song from playlists
            if (originalSong.getInstrument() != theSong.getInstrument()) {
                for (Playlist tempPlaylist : originalSong.getPlaylists()) { // remove songs from all playlists it was in
                    playlistService.deleteSongFromPlaylist(tempPlaylist, originalSong);
                }
                List<Playlist> allPlaylists = originalSong.getPlaylists();
                originalSong.getPlaylists().removeAll(allPlaylists);
            }

            // Copy changed data to original song
            originalSong.setTitle(theSong.getTitle());
            originalSong.setArtist(theSong.getArtist());
            originalSong.setInstrument(theSong.getInstrument());
            originalSong.setTuning(theSong.getTuning());
            originalSong.setVisible(theSong.getVisible());
            storageService.removeBackingTrack(theSong);
            var path = storageService.saveBackingTrack(backingTrack, theSong);
            originalSong.setBackingTrackFilename(path.toString());
            originalSong.setLength(SongsUtil.getSongDuration(path));


            songService.save(originalSong);
        } else { // song doesn't yet exist
            theSong = songService.save(theSong); //Save to get database id
            var path = storageService.saveBackingTrack(backingTrack, theSong);
            theSong.setBackingTrackFilename(path.toString());
            theSong.setLength(SongsUtil.getSongDuration(path));
            songService.save(theSong);
        }

        return "redirect:/songs?inst=ALL";
    }

    /**
     * Controller method responsible for mapping "/playlist/deleteSong"
     * @param theSongId id of song to delete
     * @param thePlaylistId id of playlist
     * @return redirects back to the playlist
     */
    @GetMapping("/songs/deleteSong")
    public String deleteSong(@RequestParam("songId") Long theSongId, @RequestParam(value = "playlistId", required = false) Long thePlaylistId) {
        // delete the song
        songService.delete(theSongId);

        if(thePlaylistId != null)
            return "redirect:/playlist?id=" + thePlaylistId;
        else
            return "redirect:/songs?inst=ALL";
    }


    /**
     * Controller method responsible for mapping "/songs/showUpdateForm".
     * Prepares data for update song form.
     *
     * @param theId    id of the song to edit
     * @param theModel handler of attributes
     * @return filename of .jsp that should be used for "/songs/showUpdateForm"
     */
    @GetMapping("/songs/showUpdateForm")
    public String showUpdateForm(@RequestParam("songId") Long theId, Model theModel) {
        // get song from database
        Song theSong = songService.getSongById(theId);
        var user = UserUtil.getCurrentUser(userService);

        // set song as a model attribute to pre-populate the form
        theModel.addAttribute("song", theSong);
        theModel.addAttribute("instruments", InstrumentEnum.values());
        theModel.addAttribute("tunings", TuningEnum.values());

        theModel.addAttribute("allowedAudioExtensions", storageService.getAllowedBackingTrackExtensions());
        theModel.addAttribute("user", user);

        // send over to the form
        return "song-form";
    }

    /**
     * Controller method responsible for mapping "/songs/showAddToPlaylistForm".
     * Prepares data for the form.
     *
     * @param theSongId id of the song to add to playlist
     * @param theModel  handler of attributes
     * @return filename of .jsp with form to add song to playlist
     */
    @GetMapping("/songs/showAddToPlaylistForm")
    public String showAddToPlaylistForm(@RequestParam("songId") Long theSongId, Model theModel) {
        // get song from database
        Song theSong = songService.getSongById(theSongId);
        var user = UserUtil.getCurrentUser(userService);

        // Select only playlist that are for the same instrument as the song is
        List<Playlist> playlists = theSong.getUser().getPlaylists();
        Predicate<Playlist> isCorrectInstrument = item -> item.getInstrument() == theSong.getInstrument();
        playlists = playlists.stream().filter(isCorrectInstrument).collect(Collectors.toList());

        // add the songs to the model
        theModel.addAttribute("user", user);
        theModel.addAttribute("song", theSong);
        theModel.addAttribute("playlists", playlists);

        return "song-to-playlist-form";
    }

    /**
     * Controller method responsible for mapping "/songs/saveSongToPlaylist".
     *
     * @param theSongId   id of song to add to playlist
     * @param thePlaylist playlist to which add song
     * @return redirect back to all songs display
     */
    @PostMapping("/songs/saveSongToPlaylist")
    public String saveSong(@ModelAttribute("songId") Long theSongId, @ModelAttribute("playlist") Playlist thePlaylist) {
        // get song from database
        Song theSong = songService.getSongById(theSongId);
        playlistService.addSongToPlaylist(thePlaylist, theSong);

        return "redirect:/songs?inst=ALL";
    }

}
