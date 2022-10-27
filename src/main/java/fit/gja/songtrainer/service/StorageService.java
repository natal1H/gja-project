package fit.gja.songtrainer.service;

import fit.gja.songtrainer.config.StorageServiceConfig;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class StorageService implements IStorageService {
    private final StorageServiceConfig config;

    public StorageService(StorageServiceConfig config) {
        this.config = config;
    }

    @Override
    public Path saveBackingTrack(MultipartFile file, Song song) throws IOException, InvalidFileExtensionException {
        String[] fileParts = file.getOriginalFilename().split("\\.");
        String extension = fileParts[fileParts.length - 1];

        if (!config.getAllowedExtensions().contains(extension))
            throw new InvalidFileExtensionException();

        File saveFile = new File(config.getFileStoragePath().toFile(), song.getId() + "." + extension);
        file.transferTo(saveFile);
        return saveFile.toPath();
    }

    @Override
    public File loadBackingTrack(Song song) {
        String backingTrackPath = song.getBackingTrackFilename();
        if(backingTrackPath == null) return null;
        return new File(backingTrackPath);
    }

}
