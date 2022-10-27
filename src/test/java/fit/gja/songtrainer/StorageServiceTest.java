package fit.gja.songtrainer;

import fit.gja.songtrainer.config.StorageServiceConfig;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import fit.gja.songtrainer.service.StorageService;
import fit.gja.songtrainer.util.Instrument;
import junit.framework.TestCase;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageServiceTest {
    private final Path filePath = Paths.get("./storageServiceTest");
    private StorageService storageService = new StorageService(
            new StorageServiceConfig(
                    filePath,
                    Lists.list("mp3")
            )
    );

    private final User user = new User("tester", "test", "test", "test", "test@test.test");
    private final Song song = new Song("Test", "Test", Instrument.InstrumentEnum.GUITAR, user);

    @Before
    public void before() {

        song.setId(0L);
        filePath.toFile().mkdirs();
    }

    @Test
    public void invalidFileExtension() throws InvalidFileExtensionException, IOException {
        MockMultipartFile file = new MockMultipartFile("testFile", "testFile.png", "audio/mpeg", (byte[]) null);

        storageService.saveBackingTrack(file, song);
    }

    @Test
    public void fileSave() throws InvalidFileExtensionException, IOException {
        MockMultipartFile file = new MockMultipartFile("testFile", "testFile.mp3", "audio/mpeg", (byte[]) null);
        storageService.saveBackingTrack(file, song);
        File mp3File = new File(filePath.toFile(), song.getId() + ".mp3");
        assertTrue(mp3File.exists());
    }

    @Test
    public void fileLoad() throws InvalidFileExtensionException, IOException {
        File file = new File(filePath.toFile(), song.getId() + ".mp3");
        file.createNewFile();
        song.setBackingTrackFilename(file.getPath().toString());
        File file2 = storageService.loadBackingTrack(song);
        assertEquals(file, file2);
    }

    @After
    public void cleanup() {
        for (File file : filePath.toFile().listFiles()) {
            file.delete();
        }
        filePath.toFile().delete();
    }
}
