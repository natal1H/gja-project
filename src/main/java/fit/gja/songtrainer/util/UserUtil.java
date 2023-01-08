package fit.gja.songtrainer.util;

import fit.gja.songtrainer.entity.Role;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.RoleService;
import fit.gja.songtrainer.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class with functions for dealing with users
 */
public class UserUtil {

    /**
     * Function to get currently signed-in user
     * @param userService service for handling users
     * @return User current user
     */
    public static User getCurrentUser(UserService userService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        return userService.findByUserName(userDetail.getUsername());
    }

    /**
     * Function to remove users
     * @param users list of users
     * @param user users to remove
     * @return new list of users
     */
    public static List<User> removeUserFromList(List<User> users, User user) {
        users.removeIf(item -> item.getId().equals(user.getId()));
        return users;
    }

    /**
     * Function to filter out non-lector users
     * @param users all users
     * @param roleService service for handling roles
     * @return list of regular users
     */
    public static List<User> removeNonLectorsFromList(List<User> users, RoleService roleService) {
        Role lectorRole = roleService.findRoleByName("ROLE_LECTOR");
        Predicate<User> isNotLectorUser = item -> item.getRoles().contains(lectorRole);
        users = users.stream().filter(isNotLectorUser).collect(Collectors.toList());
        return users;
    }
}
