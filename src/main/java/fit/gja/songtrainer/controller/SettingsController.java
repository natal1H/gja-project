package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidOldPasswordException;
import fit.gja.songtrainer.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SettingsController {

    private final UserService userService;

    public SettingsController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/settings")
    public String showSettingsPage(Model theModel) {
        // get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.findByUserName(userDetail.getUsername());

        theModel.addAttribute("user", user);

        return "settings";
    }

    @GetMapping("/settings/edit")
    public String showEditForm(Model theModel) {
        // get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.findByUserName(userDetail.getUsername());

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
        // get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.findByUserName(userDetail.getUsername());

        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            System.out.println("Stored passwd and entered passwd don't match");
            return "redirect:/settings"; // TODO: redirect to error page
        }
        userService.changeUserPassword(user, newPassword);

        return "redirect:/settings";
    }
}
