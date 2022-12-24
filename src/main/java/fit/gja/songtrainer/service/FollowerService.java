package fit.gja.songtrainer.service;

import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.FriendAlreadyExistException;
import fit.gja.songtrainer.exceptions.UserNotFoundException;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowerService {
    @Autowired
    private UserService userService;

    @Autowired
    private PlaylistService playlistService;

    public Map<User, List<Playlist>> getFollowedPlaylists() {
        var map = new HashMap<User, List<Playlist>>();
        var user = UserUtil.getCurrentUser(userService);
        for (var followed: user.getFollowed()) {
            map.put(followed, playlistService.getPublicPlaylistsForUser(followed));
        }
        return map;
    }

    public Collection<User> getFollowedUsers() {
        var user = UserUtil.getCurrentUser(userService);
        return user.getFollowed();
    }

    public void addFollow(User userToFollow) throws UserNotFoundException, FriendAlreadyExistException {
        var user = UserUtil.getCurrentUser(userService);
        if(userToFollow == null) throw new UserNotFoundException();

        if(user.getFollowed().contains(userToFollow)) throw new FriendAlreadyExistException();
        user.getFollowed().add(userToFollow);
        userService.save(user);
    }

    public void removeFollow(User followedUser) throws UserNotFoundException {
        var user = UserUtil.getCurrentUser(userService);
        if(followedUser == null) throw new UserNotFoundException();
        user.getFollowed().remove(followedUser);
        userService.save(user);
    }
}
