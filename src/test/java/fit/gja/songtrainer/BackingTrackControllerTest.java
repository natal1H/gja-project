package fit.gja.songtrainer;

import fit.gja.songtrainer.controller.BackingTracksController;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import fit.gja.songtrainer.service.StorageService;
import fit.gja.songtrainer.service.SongService;
import fit.gja.songtrainer.util.Instrument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BackingTracksController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = BackingTracksController.class)
public class BackingTrackControllerTest {
    private final User user = new User("tester", "test", "test", "test", "test@test.test");
    private final Song song = new Song("Test", "Test", Instrument.InstrumentEnum.GUITAR, user);
    private final Song song2 = new Song("Test2", "Test2", Instrument.InstrumentEnum.GUITAR, user);
    private final MockMultipartFile file = new MockMultipartFile("testFile", "testFile.png", "audio/mpeg", (byte[]) null);

    @MockBean
    private StorageService storageService;

    @MockBean
    private SongService songService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void before() throws InvalidFileExtensionException, IOException {
        Mockito.when(songService.getSongById(0L)).thenReturn(song);
        Mockito.when(songService.getSongById(1L)).thenReturn(null);
        Mockito.when(songService.getSongById(2L)).thenReturn(song2);
        Mockito.when(storageService.saveBackingTrack(Mockito.any(), Mockito.eq(song))).thenReturn(Paths.get("test/path"));
        Mockito.when(storageService.loadBackingTrack(Mockito.eq(song))).thenReturn(new File("."));
        Mockito.when(storageService.loadBackingTrack(Mockito.eq(song2))).thenReturn(null);
    }

    @Test
    public void upload() throws Exception {
        mvc.perform(
                multipart(HttpMethod.POST, "/songs/backingTrack")
                        .file("track", file.getBytes())
                        .param("songId", "0")
        ).andExpect(status().isOk());
        Mockito.verify(songService, Mockito.times(1)).getSongById(0L);
        Mockito.verify(storageService, Mockito.times(1)).saveBackingTrack(Mockito.any(), Mockito.eq(song));
    }

    @Test
    public void uploadToNonExistingSong() throws Exception {
        mvc.perform(
                multipart(HttpMethod.POST, "/songs/backingTrack")
                        .file("track", file.getBytes())
                        .param("songId", "1")
        ).andExpect(status().isNotFound());
        Mockito.verify(songService, Mockito.times(1)).getSongById(1L);
    }

    //Todo: correct download test. Need to somehow mock file to be downloaded

    @Test
    public void downloadNonExistingSong() throws Exception {
        mvc.perform(
                get("/songs/backingTrack")
                        .param("songId", "1")
        ).andExpect(status().isNotFound());
        Mockito.verify(songService, Mockito.times(1)).getSongById(1L);
    }

    @Test
    public void downloadNoBackingTrack() throws Exception {
        mvc.perform(
                get("/songs/backingTrack")
                        .param("songId", "2")
        ).andExpect(status().isNotFound());
        Mockito.verify(songService, Mockito.times(1)).getSongById(2L);
        Mockito.verify(storageService, Mockito.times(1)).loadBackingTrack(song2);
    }
}
