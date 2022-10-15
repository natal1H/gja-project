package fit.gja.songtrainer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SongtrainerController {

    @GetMapping("/")
    public String showHome() {
        return "home";
    }
}
