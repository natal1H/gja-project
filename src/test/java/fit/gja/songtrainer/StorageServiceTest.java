package fit.gja.songtrainer;

import fit.gja.songtrainer.config.StorageServiceConfig;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import fit.gja.songtrainer.service.StorageService;
import fit.gja.songtrainer.util.InstrumentEnum;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;


@Ignore
public class StorageServiceTest {
    private final Path rootPath = Paths.get("./storageServiceTest");
    private final StorageService storageService = new StorageService(
            new StorageServiceConfig(
                    rootPath,
                    Paths.get("backingTracks"), Paths.get("profilePictures"), Lists.list("mp3"),
                    Lists.list("png", "jpeg"))
    );

    private final User user = new User("tester", "test", "test", "test", "test@test.test");
    private final Song song = new Song("Test", "Test", InstrumentEnum.GUITAR, user);

    @Before
    public void before() {

        song.setId(0L);
        rootPath.toFile().mkdirs();
    }

    @Test
    public void invalidFileExtension() {
        MockMultipartFile file = new MockMultipartFile("testFile", "testFile.png", "audio/mpeg", (byte[]) null);
        assertThrows(InvalidFileExtensionException.class, () -> storageService.saveBackingTrack(file, song));
    }

    @Test
    public void fileSave() throws InvalidFileExtensionException, IOException {
        MockMultipartFile file = new MockMultipartFile("testFile", "testFile.mp3", "audio/mpeg", (byte[]) null);
        storageService.saveBackingTrack(file, song);
        File mp3File = new File(rootPath.resolve("backingTracks").toFile(), song.getId() + ".mp3");
        assertTrue(mp3File.exists());
    }

    @Test
    public void fileLoad() throws InvalidFileExtensionException, IOException {
        File file = new File(rootPath.toFile(), song.getId() + ".mp3");
        file.createNewFile();
        song.setBackingTrackFilename(file.getPath());
        File file2 = storageService.loadBackingTrack(song);
        assertEquals(file, file2);
    }

    @After
    public void cleanup() {
        for (File file : rootPath.toFile().listFiles()) {
            file.delete();
        }
        rootPath.toFile().delete();
    }
}
