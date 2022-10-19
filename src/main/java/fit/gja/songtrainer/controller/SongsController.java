package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.dao.SongDao;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class SongsController {

    @Autowired
    private SongDao songDao;

    @Autowired
    private UserService userService;

    @RequestMapping("/songs")
    public String listAllSongs(Model theModel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByUserName(userDetail.getUsername());

        System.out.println(">>> SongController.listAllSongs: before get all songs");
        // get songs from the dao
        List<Song> theSongs = songDao.getSongsByUser(u); // todo: test
        System.out.println(">>> SongController.listAllSongs: after get all songs");

        // add the songs to the model
        theModel.addAttribute("songs", theSongs);

        return "songs";
    }
}
