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

public class UserUtil {
    public static User getCurrentUser(UserService userService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        return userService.findByUserName(userDetail.getUsername());
    }

    public static List<User> removeUserFromList(List<User> users, User user) {
        users.removeIf(item -> item.getId().equals(user.getId()));
        return users;
    }

    public static List<User> removeNonLectorsFromList(List<User> users, RoleService roleService) {
        Role lectorRole = roleService.findRoleByName("ROLE_LECTOR");
        Predicate<User> isNotLectorUser = item -> item.getRoles().contains(lectorRole);
        users = users.stream().filter(isNotLectorUser).collect(Collectors.toList());
        return users;
    }
}
