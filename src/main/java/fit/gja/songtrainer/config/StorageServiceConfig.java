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

    public StorageServiceConfig(Path fileStoragePath, List<String> allowedExtensions) {
        this.fileStoragePath = fileStoragePath;
        this.allowedExtensions = allowedExtensions;
    }

    private Path fileStoragePath;
    private List<String> allowedExtensions;

    public Path getFileStoragePath() {
        return fileStoragePath;
    }

    public void setFileStoragePath(Path fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
    }

    public List<String> getAllowedExtensions() {
        return allowedExtensions;
    }

    public void setAllowedExtensions(List<String> allowedExtensions) {
        this.allowedExtensions = allowedExtensions;
    }
}
