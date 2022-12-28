package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.exceptions.NoBackingTrackException;
import fit.gja.songtrainer.exceptions.SongNotFoundException;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class BackingTracksController {
    private final SongService songService;

    private final StorageService storageService;
    private final UserService userService;

    public BackingTracksController(SongService songService, StorageService storageService, UserService userService) {
        this.songService = songService;
        this.storageService = storageService;
        this.userService = userService;
    }

    @GetMapping("/songs/backingTrack")
    public ResponseEntity<FileSystemResource> getBackingTrack(@RequestParam(value = "songId") Long songId) throws IOException, SongNotFoundException, NoBackingTrackException {
        Song song = songService.getSongById(songId);
        if(song == null) throw new SongNotFoundException();
        File track = storageService.loadBackingTrack(song);
        if(track == null) throw new NoBackingTrackException();
        String contentType = Files.probeContentType(track.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        return new ResponseEntity<>(new FileSystemResource(track), headers, HttpStatus.OK);
    }

    @GetMapping("/song")
    public String getPlayPage(@RequestParam(value = "songId") Long songId, Model model) {
        var song = songService.getSongById(songId);
        var user = UserUtil.getCurrentUser(userService);
        model.addAttribute("song", song);
        model.addAttribute("user", user);

        return "song-play";
    }

    @PostMapping("/songs/addTimesPlayed")
    public void addTimesPlayed(@RequestParam(value = "songId") Long songId) {
        var song = songService.getSongById(songId);
        song.setTimes_played(song.getTimes_played() + 1);
        songService.save(song);
    }
}
