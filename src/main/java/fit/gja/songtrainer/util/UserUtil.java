package fit.gja.songtrainer.util;

import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtil {
    public static User getCurrentUser(UserService userService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        return userService.findByUserName(userDetail.getUsername());
    }
}
