package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Role;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.RoleService;
import fit.gja.songtrainer.service.UserService;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class SettingsController {

    private final UserService userService;
    private final RoleService roleService;

    public SettingsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/settings")
    public String showSettingsPage(Model theModel) {
        User user = UserUtil.getCurrentUser(userService);
        theModel.addAttribute("user", user);
        return "settings";
    }

    @GetMapping("/settings/edit")
    public String showEditForm(Model theModel) {
        User user = UserUtil.getCurrentUser(userService);

        // set song as a model attribute to pre-populate the form
        theModel.addAttribute("user", user);

        // send over to the form
        return "user-info-edit-form";
    }

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

    @RequestMapping(value = "/settings/changePassword")
    public String showChangePasswordForm() {
        return "password-change-form";
    }

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

    @PostMapping("/settings/stopBeingLector")
    public String stopBeingLector() {
        User user = UserUtil.getCurrentUser(userService);

        Collection<Role> usersRoles = user.getRoles();
        Role lectorRole = roleService.findRoleByName("ROLE_LECTOR");
        if (usersRoles.contains(lectorRole)) {
            // user has LECTOR role, remove it
            usersRoles.remove(lectorRole);
            user.setRoles(usersRoles);
            // TODO! when table `lector_has_student` & `student_has_lector`, clean up lector from these!
            userService.save(user);
        }

        return "redirect:/showLoginPage";
    }
}
