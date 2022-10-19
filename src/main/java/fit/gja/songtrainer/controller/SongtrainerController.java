package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.dao.PlaylistDao;
import fit.gja.songtrainer.entity.InstrumentConverter;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SongtrainerController {

    @Autowired
    PlaylistDao playlistDao;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showHome(Model theModel) {
        // Get list of user's playlists
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByUserName(userDetail.getUsername());

        // get songs from the dao
        List<Playlist> thePlaylists = playlistDao.getPlaylistsByUser(u);

        // add the songs to the model
        theModel.addAttribute("playlists", thePlaylists);

        return "home";
    }

    // add request mapping for /lectors
    @GetMapping("/lectors")
    public String showLectors() {
        return "lectors";
    }
}
