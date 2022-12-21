package fit.gja.songtrainer.service;

import fit.gja.songtrainer.config.StorageServiceConfig;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class StorageService {
    private final StorageServiceConfig config;

    public StorageService(StorageServiceConfig config) {
        this.config = config;
    }

    private String checkFileExtensions(MultipartFile file, List<String> extensions) throws InvalidFileExtensionException {
        String[] fileParts = file.getOriginalFilename().split("\\.");
        String extension = fileParts[fileParts.length - 1];

        if (!extensions.contains(extension))
            throw new InvalidFileExtensionException();
        return extension;
    }

    public Path saveBackingTrack(MultipartFile file, Song song) throws IOException, InvalidFileExtensionException {
        File backingTrackDir = config.getFileStorageRootPath().resolve(config.getBackingTrackPath()).toFile();
        if (!backingTrackDir.exists()) backingTrackDir.mkdirs(); //Create directory path if not exist

        String extension = checkFileExtensions(file, config.getAllowedBackingTrackExtensions());
        File saveFile = new File(backingTrackDir, song.getId() + "." + extension);
        file.transferTo(saveFile);
        return saveFile.toPath();
    }

    public Path saveProfilePicture(MultipartFile file, User user) throws IOException, InvalidFileExtensionException {
        File profilePictureDir = config.getFileStorageRootPath().resolve(config.getProfilePicturePath()).toFile();
        if (!profilePictureDir.exists()) profilePictureDir.mkdirs(); //Create directory path if not exist

        String extension = checkFileExtensions(file, config.getAllowedProfilePictureExtensions());
        File saveFile = new File(profilePictureDir, user.getId() + "." + extension);
        file.transferTo(saveFile);
        return saveFile.toPath();
    }

    public File loadBackingTrack(Song song) {
        String backingTrackPath = song.getBackingTrackFilename();
        if (backingTrackPath == null) return null;
        File backingTrackFile = new File(backingTrackPath);
        if (backingTrackFile.exists())
            return backingTrackFile;
        else return null;
    }

    public File loadProfilePicture(User user) {
        String profilePicturePath = user.getProfilePicturePath();
        if (profilePicturePath == null) return null;
        File file = new File(profilePicturePath);
        if (file.exists())
            return file;
        else return null;
    }

    public void removeProfilePicture(User user) {
        File picture = loadProfilePicture(user);
        if (picture == null) return;
        picture.delete();
    }

}
