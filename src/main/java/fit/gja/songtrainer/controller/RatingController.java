package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.*;
import fit.gja.songtrainer.service.*;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RatingController {

    private final RatingService ratingService;

    private final UserService userService;

    private final SongService songService;

    /**
     * Class constructor, injects the necessary services
     * @param ratingService Service handling database request about ratings
     * @param userService Service handling database request about users
     */
    public RatingController(RatingService ratingService, UserService userService,SongService songService) {
        this.ratingService = ratingService;
        this.userService = userService;
        this.songService = songService;
    }

    @RequestMapping(value = "/rating/showAll", method = RequestMethod.GET)
    public ModelAndView showRatings(@RequestParam("songId") Long theId) {
        // Get list of song's ratings
        ModelAndView mav = new ModelAndView();

        User user = UserUtil.getCurrentUser(userService);
        Song song = songService.getSongById(theId);
        if(user != song.getUser()){
            mav.setViewName("access-denied");
            return mav;
        }
        List<Rating> ratings = song.getRatings();


        // add the songs to the model
        mav.addObject("ratings", ratings);
        mav.addObject("song", song);
        mav.addObject("user", user);

        mav.setViewName("ratings");

        return mav;
    }

    @GetMapping("/rating/addRating")
    public String showFormForAddRating(@RequestParam("songId") Long theSongId,Model theModel) {

        // create model attribute to bind form data
        Song theSong = songService.getSongById(theSongId);
        Rating theRating = new Rating();

        User user = UserUtil.getCurrentUser(userService);

        theModel.addAttribute("song", theSong);
        theModel.addAttribute("rating", theRating);
        theModel.addAttribute("user", user);

        return "rating-form";
    }

    @PostMapping("/rating/saveRating")
    public String saveRating(@ModelAttribute("rating") Rating theRating,@RequestParam("songId") Long theSongId) {
        Song song = songService.getSongById(theSongId);
        // try to see if rating already exists:
        if (theRating.getId() != null) {
            // Find original rating in db
            Rating originalRating = ratingService.getRatingById(theRating.getId());
            originalRating.setFeelings(theRating.getFeelings());
            originalRating.setAccuracy(theRating.getAccuracy());
            originalRating.setNumberMistakes(theRating.getNumberMistakes());
            originalRating.setComment(theRating.getComment());
            //save new values to original rating
            ratingService.save(originalRating);
        } else {
            //save new rating
            if (!(song.getRatings().contains(theRating))) {
                song.getRatings().add(theRating);
            }
            ratingService.save(theRating);
        }


        return "redirect:/rating/showAll?songId=" + song.getId();
    }
    //hodnotenie spravit ako v playlist, z songu vytiahnut user_id a ak sa rovna s current user tak mozem hodnotit, inak error



}
