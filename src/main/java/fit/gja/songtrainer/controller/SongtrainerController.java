package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.*;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller class responsible for request mappings coming from the home page.
 */
@Controller
public class SongtrainerController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * Controller method responsible for mapping "/"
     * @param theModel holder of attributes
     * @return filename of .jsp that should be used for "/"
     */
    @GetMapping("/")
    @Transactional
    public String showHome(Model theModel) {
        // Get list of user's playlists
        User user = UserUtil.getCurrentUser(userService);

        // add the songs to the model
        theModel.addAttribute("playlists", user.getPlaylists());
        theModel.addAttribute("user", user);

        return "home";
    }

    /**
     * Controller method responsible for mapping "/lectors".
     * This mapping is only accessible for users with ROLE_LECTOR.
     * @return filename of .jsp that should be used for "/lectors"
     */
    @GetMapping("/lectors")
    public String showLectors(Model theModel) {
        // Get list of user's students
        User user = UserUtil.getCurrentUser(userService);

        // add the songs to the model
        theModel.addAttribute("students", user.getStudents());
        theModel.addAttribute("user", user);

        return "lectors";
    }

    /**
     * Controller method responsible for mapping "/lectors/removeStudent".
     * @param theLectorId User id of lector
     * @param theStudentId User id of student to remove
     * @return redirects back to the lector page
     */
    @GetMapping("/lectors/removeStudent")
    public String removeStudent(@RequestParam("lectorId") Long theLectorId, @RequestParam("studentId") Long theStudentId) {
        User student = userService.getUserById(theStudentId);
        User lector = userService.getUserById(theLectorId);
        userService.removeLectorStudent(student, lector);

        return "redirect:/lectors";
    }

    @RequestMapping(value = "/lectors/search", method = RequestMethod.POST)
    public String searchStudents(@RequestParam("keyword") String keyword, Model theModel) {
        User user = UserUtil.getCurrentUser(userService);
        List<User> users = userService.findUserByName(keyword);
        users = UserUtil.removeUserFromList(users, user);

        // add the songs to the model
        theModel.addAttribute("students", user.getStudents());
        theModel.addAttribute("user", user);
        theModel.addAttribute("users", users);

        return "lectors";
    }

    /**
     * Controller method responsible for mapping "/students".
     * This mapping is accessible for all users.
     * @return filename of .jsp that should be used for "/students"
     */
    @GetMapping("/students")
    public String showStudents(Model theModel) {
        // Get list of user's students
        User user = UserUtil.getCurrentUser(userService);

        // add the songs to the model
        theModel.addAttribute("lectors", user.getLectors());
        theModel.addAttribute("user", user);

        return "students";
    }

    /**
     * Controller method responsible for mapping "/students/removeLector".
     * @param theLectorId User id of the lector to remove
     * @param theStudentId User id of the student
     * @return redirects back to the student page
     */
    @GetMapping("/students/removeLector")
    public String removeLector(@RequestParam("lectorId") Long theLectorId, @RequestParam("studentId") Long theStudentId) {
        User student = userService.getUserById(theStudentId);
        User lector = userService.getUserById(theLectorId);
        userService.removeLectorStudent(student, lector);

        return "redirect:/students";
    }

    /**
     * @param keyword
     * @param theModel
     * @return
     */
    @RequestMapping(value = "/students/search", method = RequestMethod.POST)
    public String searchLectors(@RequestParam("keyword") String keyword, Model theModel) {
        User user = UserUtil.getCurrentUser(userService);
        List<User> users = userService.findUserByName(keyword);
        users = UserUtil.removeUserFromList(users, user);
        users = UserUtil.removeNonLectorsFromList(users, roleService);

        // add the songs to the model
        theModel.addAttribute("lectors", user.getLectors());
        theModel.addAttribute("user", user);
        theModel.addAttribute("users", users);

        return "students";
    }

    /**
     * @param keyword Keyword by which to search for user
     * @param theModel handler of attributes
     * @return home page
     */
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String searchUsers(@RequestParam("keyword") String keyword, Model theModel) {
        User user = UserUtil.getCurrentUser(userService);
        List<User> users = userService.findUserByName(keyword);

        theModel.addAttribute("users", users);
        theModel.addAttribute("playlists", user.getPlaylists());
        theModel.addAttribute("user", user);

        return "home";
    }

    /**
     * @param theLectorId id of the lector
     * @param theStudentId id of the student
     * @return redirects back to the lector page
     */
    @GetMapping("/addStudent")
    public String addStudent(@RequestParam("lectorId") Long theLectorId, @RequestParam("studentId") Long theStudentId) {
        User student = userService.getUserById(theStudentId);
        User lector = userService.getUserById(theLectorId);
        userService.addStudentLector(student, lector);

        return "redirect:/lectors";
    }

    /**
     * @param theLectorId id of the lector
     * @param theStudentId id of the student
     * @return redirects back to the student page
     */
    @GetMapping("/addLector")
    public String addLector(@RequestParam("lectorId") Long theLectorId, @RequestParam("studentId") Long theStudentId) {
        User student = userService.getUserById(theStudentId);
        User lector = userService.getUserById(theLectorId);
        userService.addStudentLector(student, lector);

        return "redirect:/students";
    }
}
