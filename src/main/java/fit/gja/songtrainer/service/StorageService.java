package fit.gja.songtrainer.service;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    Path saveBackingTrack(MultipartFile file, Song song) throws IOException, InvalidFileExtensionException;

    Path saveProfilePicture(MultipartFile file, User user) throws IOException, InvalidFileExtensionException;

    File loadBackingTrack(Song song);

    File loadProfilePicture(User user);

    void removeProfilePicture(User user);
}
