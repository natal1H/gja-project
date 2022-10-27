package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import fit.gja.songtrainer.service.SongService;
import fit.gja.songtrainer.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class BackingTracksController {
    @Autowired
    private SongService songService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/songs/backingTrack")
    public void uploadBackingTrack(@RequestParam(value = "songId") Long songId, @RequestParam(value = "track") MultipartFile track) throws IOException, InvalidFileExtensionException {
        Song song = songService.getSongById(songId);
        Path savedPath = storageService.saveBackingTrack(track, song);
        song.setBackingTrackFilename(savedPath.toString());
        songService.save(song);
    }

    @GetMapping("/songs/backingTrack")
    public ResponseEntity<FileSystemResource> getBackingTrack(@RequestParam(value = "songId") Long songId) throws IOException {
        Song song = songService.getSongById(songId);
        File track = storageService.loadBackingTrack(song);
        String contentType = Files.probeContentType(track.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        return new ResponseEntity<>(new FileSystemResource(track), headers, HttpStatus.OK);
    }
}
