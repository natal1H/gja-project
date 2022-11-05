package fit.gja.songtrainer;

import fit.gja.songtrainer.controller.ProfilePictureController;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import fit.gja.songtrainer.service.StorageService;
import fit.gja.songtrainer.service.UserService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProfilePictureController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = ProfilePictureController.class)
public class ProfilePictureControllerTest {
    private final User user = new User("tester", "test", "test", "test", "test@test.test");
    private final MockMultipartFile file = new MockMultipartFile("testFile", "testFile.png", "image/png", (byte[]) null);

    @MockBean
    private StorageService storageService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void before() throws InvalidFileExtensionException, IOException {
        Mockito.when(userService.findByUserName(Mockito.any())).thenReturn(user);
        Mockito.when(storageService.saveProfilePicture(Mockito.any(), Mockito.eq(user))).thenReturn(Paths.get("test/path"));
        Mockito.when(storageService.loadProfilePicture(Mockito.eq(user))).thenReturn(null);
    }

    @Test
    @WithMockUser(username = "tester")
    public void upload() throws Exception {
        mvc.perform(
                multipart(HttpMethod.POST, "/profilePicture")
                        .file("picture", file.getBytes())
        ).andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).findByUserName("tester");
        Mockito.verify(storageService, Mockito.times(1)).saveProfilePicture(Mockito.any(), Mockito.eq(user));
    }

    @Test
    @WithMockUser(username = "tester")
    public void downloadPicture() throws Exception {
        mvc.perform(
                get("/profilePicture")
        ).andExpect(status().isNotFound());
        Mockito.verify(userService, Mockito.times(1)).findByUserName("tester");
        Mockito.verify(storageService, Mockito.times(1)).loadProfilePicture(user);
    }

    @Test
    @WithMockUser(username = "tester")
    public void deletePicture() throws Exception {
        mvc.perform(
                delete("/profilePicture")
        ).andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).findByUserName("tester");
        Mockito.verify(storageService, Mockito.times(1)).removeProfilePicture(user);
    }
}
