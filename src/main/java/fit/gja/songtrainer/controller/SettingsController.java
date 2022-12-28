package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Role;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.*;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Controller class responsible for handling request from user settings page.
 */
@Controller
public class SettingsController {

    private final UserService userService;
    private final RoleService roleService;
    private final StorageService storageService;

    /**
     * Class constructor, injects the necessary services
     * @param userService Service handling database request about users
     * @param roleService Service handling database request about roles
     */
    public SettingsController(UserService userService, RoleService roleService, StorageService storageService) {
        this.userService = userService;
        this.roleService = roleService;
        this.storageService = storageService;
    }

    /**
     * Controller method responsible for mapping "/settings".
     * @param theModel handler of attributes
     * @return "settings.jsp"
     */
    @RequestMapping(value = "/settings")
    public String showSettingsPage(Model theModel) {
        User user = UserUtil.getCurrentUser(userService);
        theModel.addAttribute("allowedProfilePictureFormats", storageService.getAllowedProfilePictureExtensions());
        theModel.addAttribute("user", user);
        return "settings";
    }

    /**
     * Controller method responsible for mapping "/settings/edit".
     * @param theModel handler of attributes
     * @return user settings edit form
     */
    @GetMapping("/settings/edit")
    public String showEditForm(Model theModel) {
        User user = UserUtil.getCurrentUser(userService);

        // set song as a model attribute to pre-populate the form
        theModel.addAttribute("user", user);

        // send over to the form
        return "user-info-edit-form";
    }

    /**
     * Controller method responsible for mapping "/settings/saveEditedInfo".
     * Saves new user info to database.
     * @param theUser object containing new user information
     * @return redirects back to the user settings page
     */
    @PostMapping("/settings/saveEditedInfo")
    public String saveSong(@ModelAttribute("user") User theUser) {
        User originalUser = userService.getUserById(theUser.getId());
        // Copy changed details to original user
        originalUser.setUserName(theUser.getUserName());
        originalUser.setFirstName(theUser.getFirstName());
        originalUser.setLastName(theUser.getLastName());
        originalUser.setEmail(theUser.getEmail());
        // Save changed user
        userService.save(originalUser);

        return "redirect:/settings";
    }

    /**
     * Controller method responsible for mapping "/settings/changePassword.
     * @return page for password change
     */
    @RequestMapping(value = "/settings/changePassword")
    public String showChangePasswordForm() {
        return "password-change-form";
    }

    /**
     * Controller method responsible for mapping "/settings/updatePassword".
     * Updates user's password, requires re-login after
     * @param newPassword new password
     * @param passwordConfirm password confirmation
     * @param oldPassword old password
     * @return redirects to login page
     */
    // TODO warning, receives plaintext passwords - fix it!
    // TODO doesn't check if new & confirm pass matches
    @PostMapping("/settings/updatePassword")
    public String changeUserPassword(@RequestParam("password") String newPassword, @RequestParam("passwordConfirm") String passwordConfirm, @RequestParam("oldpassword") String oldPassword) {
        User user = UserUtil.getCurrentUser(userService);

        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            System.out.println("Stored passwd and entered passwd don't match");
            return "redirect:/settings"; // TODO: redirect to error page
        }
        userService.changeUserPassword(user, newPassword);

        return "redirect:/showLoginPage";
    }

    /**
     * Controller method responsible for mapping "/settings/becomeLector".
     * @return redirects to login page
     */
    @PostMapping("/settings/becomeLector")
    public String becomeLector() {
        User user = UserUtil.getCurrentUser(userService);

        Collection<Role> usersRoles = user.getRoles();
        Role lectorRole = roleService.findRoleByName("ROLE_LECTOR");
        if (!usersRoles.contains(lectorRole)) {
            // user doesn't have LECTOR role, add it
            usersRoles.add(lectorRole);
            user.setRoles(usersRoles);
            userService.save(user);
        }

        return "redirect:/showLoginPage";
    }

    /**
     * Controller method responsible for mapping "/settings/stopBeingLector".
     * @return redirects to the login page
     */
    @PostMapping("/settings/stopBeingLector")
    public String stopBeingLector() {
        User user = UserUtil.getCurrentUser(userService);

        Collection<Role> usersRoles = user.getRoles();
        Role lectorRole = roleService.findRoleByName("ROLE_LECTOR");
        if (usersRoles.contains(lectorRole)) {
            // user has LECTOR role, remove it
            usersRoles.remove(lectorRole);
            user.setRoles(usersRoles);
            Collection<User> students = user.getStudents();
            for (User tempStudent: students) {
                userService.removeLectorStudent(tempStudent, user);
            }
            userService.save(user);
        }

        return "redirect:/showLoginPage";
    }
}
