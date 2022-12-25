package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Rating;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.RatingService;
import fit.gja.songtrainer.service.UserService;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RatingController {

    private final RatingService ratingService;

    private final UserService userService;

    /**
     * Class constructor, injects the necessary services
     * @param ratingService Service handling database request about ratings
     * @param userService Service handling database request about users
     */
    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @GetMapping("/ratings")
    @Transactional
    public String showRatings(Model theModel) {
        // Get list of user's playlists
        User user = UserUtil.getCurrentUser(userService);

        // add the songs to the model
        theModel.addAttribute("ratings", user.getRatings());
        theModel.addAttribute("user", user);

        return "ratings";
    }

    @GetMapping("/rating/addRating")
    public String showFormForAddRating(Model theModel) {

        // create model attribute to bind form data
        Rating theRating = new Rating();

        theModel.addAttribute("rating", theRating);

        return "rating-form";
    }


}
