package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.exceptions.NoBackingTrackException;
import fit.gja.songtrainer.exceptions.SongNotFoundException;
import fit.gja.songtrainer.service.PlaylistService;
import fit.gja.songtrainer.service.SongService;
import fit.gja.songtrainer.service.StorageService;
import fit.gja.songtrainer.service.UserService;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Instant;

/**
 * Backing track handling
 */
@Controller
public class BackingTracksController {
    private final SongService songService;

    private final StorageService storageService;
    private final UserService userService;
    private final PlaylistService playlistService;

    public BackingTracksController(SongService songService, StorageService storageService, UserService userService, PlaylistService playlistService) {
        this.songService = songService;
        this.storageService = storageService;
        this.userService = userService;
        this.playlistService = playlistService;
    }

    /**
     * Gets backing track for specified song
     * @param songId id of song
     * @return backing track stream for specified song
     * @throws IOException on io error
     * @throws SongNotFoundException when song id is not found
     * @throws NoBackingTrackException when there is no backing track
     */
    @GetMapping("/songs/backingTrack")
    public ResponseEntity<FileSystemResource> getBackingTrack(@RequestParam(value = "songId") Long songId) throws IOException, SongNotFoundException, NoBackingTrackException {
        Song song = songService.getSongById(songId);
        if (song == null) throw new SongNotFoundException();
        File track = storageService.loadBackingTrack(song);
        if (track == null) throw new NoBackingTrackException();
        String contentType = Files.probeContentType(track.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        return new ResponseEntity<>(new FileSystemResource(track), headers, HttpStatus.OK);
    }

    /**
     * Shows backing track playback page
     * @param songId id of playing song
     * @param model ui model
     * @param playlistId id of playing playlist or null if single song is played
     * @return ui view
     */
    @GetMapping("/song")
    public String getPlayPage(@RequestParam(value = "songId") Long songId, Model model, @RequestParam(value = "playlistId", required = false) Long playlistId) {
        var song = songService.getSongById(songId);
        var user = UserUtil.getCurrentUser(userService);
        Long nextSong = null;

        if(playlistId != null) {
            var playlist = playlistService.getPlaylistById(playlistId);
            if(playlist == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist with id: " + playlistId + " not found");
            var songIndex = playlist.getSongs().indexOf(song);
            if(songIndex < 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Playlist with id: " + playlistId + " does not contain song with id: " + song.getId());
            if(songIndex + 1 < playlist.getSongs().size())
                nextSong = playlist.getSongs().get(songIndex + 1).getId();
        }

        model.addAttribute("song", song);
        model.addAttribute("user", user);
        model.addAttribute("nextSongId", nextSong);
        model.addAttribute("playlistId", playlistId);

        return "song-play";
    }

    /**
     * Increments played counter and sets last played date.
     * @param songId id of played song
     */
    @PostMapping("/songs/addTimesPlayed")
    public void addTimesPlayed(@RequestParam(value = "songId") Long songId) {
        var song = songService.getSongById(songId);
        song.setTimes_played(song.getTimes_played() + 1);
        song.setLast_played(Date.from(Instant.now()));
        songService.save(song);
    }
}
