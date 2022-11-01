package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.UserService;
import fit.gja.songtrainer.util.Instrument;
import fit.gja.songtrainer.util.Tuning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserSettingsController {

    private final UserService userService;

    public UserSettingsController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/settings")
    public String showSettingsPage(Model theModel) {
        // get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.findByUserName(userDetail.getUsername());

        theModel.addAttribute("user", user);

        return "user-settings";
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
        return "user-settings-form";
    }

    @PostMapping("/settings/saveEditedInfo")
    public String saveSong(@ModelAttribute("user") User theUser) {

        return "redirect:/settings";
    }
}
