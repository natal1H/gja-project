package fit.gja.songtrainer.service;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface IStorageService {
    Path saveBackingTrack(MultipartFile file, Song song) throws IOException, InvalidFileExtensionException;

    File loadBackingTrack(Song song);
}
