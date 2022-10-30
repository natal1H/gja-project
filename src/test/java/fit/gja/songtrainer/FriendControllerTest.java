package fit.gja.songtrainer;

import fit.gja.songtrainer.controller.FriendsController;
import fit.gja.songtrainer.dao.UserDao;
import fit.gja.songtrainer.entity.User;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FriendsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = FriendsController.class)
public class FriendControllerTest {
    private final User user = new User("test", "test", "test", "test", "test@test.test");
    private final User user2 = new User("test2", "test2", "test2", "test2", "test2@test.test");
    private final User user3 = new User("test3", "test3", "test3", "test3", "test3@test.test");

    @MockBean
    private UserDao users;
    @Autowired
    private MockMvc mvc;

    @Before
    public void before() {
        user.setFriends(new ArrayList<>());
        user2.setFriends(new ArrayList<>(Lists.list(user3, user)));

        Mockito.when(users.findByUserName("test")).thenReturn(user);
        Mockito.when(users.findByUserName("test2")).thenReturn(user2);
        Mockito.when(users.findByUserName("test3")).thenReturn(user3);
    }


    @Test
    @WithMockUser(username = "test2")
    public void getFriends() throws Exception {
        mvc.perform(
                get("/friends")
        ).andExpect(status().isOk()).andExpectAll(
                jsonPath("*.firstName", is(Arrays.asList("test3", "test"))),
                jsonPath("*.lastName", is(Arrays.asList("test3", "test"))),
                jsonPath("*.email").doesNotExist(),
                jsonPath("*.password").doesNotExist()
        );
    }

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    public void addNonExistingFriend() throws Exception {
        mvc.perform(
                post("/friends/add").param("friendUsername", "notExistingUsername")
        ).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    public void addFriend() throws Exception {
        mvc.perform(
                post("/friends/add").param("friendUsername", "test2")
        ).andExpect(status().isOk());

        mvc.perform(
                get("/friends")
        ).andExpect(status().isOk()).andExpectAll(
                jsonPath("*.firstName", is(Collections.singletonList("test2"))),
                jsonPath("*.email").doesNotExist(),
                jsonPath("*.password").doesNotExist()
        );
    }

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    public void addAlreadyExistingFriend() throws Exception {
        mvc.perform(
                post("/friends/add").param("friendUsername", "test2")
        ).andExpect(status().isOk());

        mvc.perform(
                post("/friends/add").param("friendUsername", "test2")
        ).andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(username = "test")
    public void removeNonExistingFriend() throws Exception {
        mvc.perform(
                post("/friends/remove").param("friendUsername", "nonExistingUsername")
        ).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test2")
    public void removeFriend() throws Exception {
        mvc.perform(
                post("/friends/remove").param("friendUsername", "test3")
        ).andExpect(status().isOk());

        mvc.perform(
                get("/friends")
        ).andExpect(status().isOk()).andExpectAll(
                jsonPath("*.firstName", is(Collections.singletonList("test"))),
                jsonPath("*.email").doesNotExist(),
                jsonPath("*.password").doesNotExist()
        );
    }
}
