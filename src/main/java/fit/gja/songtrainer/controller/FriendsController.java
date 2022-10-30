package fit.gja.songtrainer.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fit.gja.songtrainer.dao.UserDao;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.FriendAlreadyExistException;
import fit.gja.songtrainer.exceptions.UserNotFoundException;
import fit.gja.songtrainer.util.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/friends")
public class FriendsController {
    @Autowired
    private UserDao users;

    @GetMapping
    @JsonView(View.Public.class)
    public Collection<User> getFriends() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUserName(userDetails.getUsername());
        return user.getFriends();
    }

    @PostMapping("add")
    public void addFriend(@RequestParam String friendUsername) throws UserNotFoundException, FriendAlreadyExistException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUserName(userDetails.getUsername());
        User friend = users.findByUserName(friendUsername);
        if(friend == null) throw new UserNotFoundException();

        if(user.getFriends().contains(friend)) throw new FriendAlreadyExistException();
        user.getFriends().add(friend);
        users.save(user);
    }

    @PostMapping("remove")
    public void removeFriend(@RequestParam String friendUsername) throws UserNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUserName(userDetails.getUsername());
        User friend = users.findByUserName(friendUsername);
        if(friend == null) throw new UserNotFoundException();
        user.getFriends().remove(friend);
        users.save(user);
    }
}
