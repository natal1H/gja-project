package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.*;
import fit.gja.songtrainer.service.PlaylistService;
import fit.gja.songtrainer.service.SongService;
import fit.gja.songtrainer.service.UserService;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.util.SongsUtil;
import fit.gja.songtrainer.util.Tuning.TuningEnum;
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

    private final SongService songService;

    private final PlaylistService playlistService;

    private final UserService userService;

    public SongsController(SongService songService, PlaylistService playlistService, UserService userService) {
        this.songService = songService;
        this.playlistService = playlistService;
        this.userService = userService;
    }


    @RequestMapping(value = "/songs", method = RequestMethod.GET)
    public ModelAndView listSongs(@RequestParam("inst") String instrumentStr, @RequestParam("sort") String sortStr) {
        ModelAndView mav = new ModelAndView();

        // get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByUserName(userDetail.getUsername());

        // depending on GET param choose which type of songs to get
        List<Song> theSongs = SongsUtil.getUsersSongsSorted(songService, u, sortStr);

        if (!InstrumentEnum.isValidStr(instrumentStr) && !instrumentStr.equals("ALL")) {
            mav.setViewName("access-denied");
            return mav;
        }
        theSongs = SongsUtil.filterSongsByInstrument(theSongs, instrumentStr);

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
    // TODO - remove logic from controller and do it in service
    @PostMapping("/songs/saveSong")
    public String saveSong(@ModelAttribute("song") Song theSong) {

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

            songService.save(originalSong);
        } else { // song doesn't yet exist
            songService.save(theSong);
        }

        return "redirect:/songs?inst=ALL&sort=ArtistASC";
    }

    @GetMapping("/songs/delete")
    public String deleteSong(@RequestParam("songId") Long theId) {
        // delete the song
        songService.delete(theId);

        return "redirect:/songs?inst=ALL&sort=ArtistASC";
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

    @GetMapping("/songs/showAddToPlaylistForm")
    public String showAddToPlaylistForm(@RequestParam("songId") Long theSongId, Model theModel) {
        // get song from database
        Song theSong = songService.getSongById(theSongId);

        // Select only playlist that are for the same instrument as the song is
        List<Playlist> playlists = theSong.getUser().getPlaylists();
        Predicate<Playlist> isCorrectInstrument = item -> item.getInstrument() == theSong.getInstrument();
        playlists = playlists.stream().filter(isCorrectInstrument).collect(Collectors.toList());

        // add the songs to the model
        theModel.addAttribute("song", theSong);
        theModel.addAttribute("playlists", playlists);

        return "song-to-playlist-form";
    }

    @PostMapping("/songs/saveSongToPlaylist")
    public String saveSong(@ModelAttribute("songId") Long theSongId, @ModelAttribute("playlist") Playlist thePlaylist) {
        // get song from database
        Song theSong = songService.getSongById(theSongId);
        playlistService.addSongToPlaylist(thePlaylist, theSong);

        return "redirect:/songs?inst=ALL&sort=ArtistASC";
    }
}
