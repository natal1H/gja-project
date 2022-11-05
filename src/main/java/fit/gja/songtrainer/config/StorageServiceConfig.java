package fit.gja.songtrainer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.nio.file.Path;
import java.util.List;

@ConfigurationProperties("app.storage")
@ConfigurationPropertiesScan
public class StorageServiceConfig {
    public StorageServiceConfig() {
    }

    public StorageServiceConfig(Path fileStorageRootPath, Path backingTrackPath, Path profilePicturePath, List<String> allowedBackingTrackExtensions, List<String> allowedProfilePictureExtensions) {
        this.fileStorageRootPath = fileStorageRootPath;
        this.backingTrackPath = backingTrackPath;
        this.profilePicturePath = profilePicturePath;
        this.allowedBackingTrackExtensions = allowedBackingTrackExtensions;
        this.allowedProfilePictureExtensions = allowedProfilePictureExtensions;
    }

    private Path fileStorageRootPath;
    private Path backingTrackPath;
    private Path profilePicturePath;

    private List<String> allowedBackingTrackExtensions;
    private List<String> allowedProfilePictureExtensions;

    public Path getFileStorageRootPath() {
        return fileStorageRootPath;
    }

    public Path getBackingTrackPath() {
        return backingTrackPath;
    }

    public void setBackingTrackPath(Path backingTrackPath) {
        this.backingTrackPath = backingTrackPath;
    }

    public void setProfilePicturePath(Path profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public void setFileStorageRootPath(Path fileStorageRootPath) {
        this.fileStorageRootPath = fileStorageRootPath;
    }

    public List<String> getAllowedBackingTrackExtensions() {
        return allowedBackingTrackExtensions;
    }

    public void setAllowedBackingTrackExtensions(List<String> allowedBackingTrackExtensions) {
        this.allowedBackingTrackExtensions = allowedBackingTrackExtensions;
    }

    public Path getProfilePicturePath() {
        return profilePicturePath;
    }

    public List<String> getAllowedProfilePictureExtensions() {
        return allowedProfilePictureExtensions;
    }

    public void setAllowedProfilePictureExtensions(List<String> allowedProfilePictureExtensions) {
        this.allowedProfilePictureExtensions = allowedProfilePictureExtensions;
    }
}
