package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import fit.gja.songtrainer.form.StudentSongForm;
import fit.gja.songtrainer.service.*;
import fit.gja.songtrainer.util.InstrumentEnum;
import fit.gja.songtrainer.util.SongsUtil;
import fit.gja.songtrainer.util.Tuning;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
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

    @Autowired
    private FollowerService followerService;

    @Autowired
    private SongService songService;

    @Autowired
    private StorageService storageService;

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
        theModel.addAttribute("followed", followerService.getFollowedPlaylists());

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
        theModel.addAttribute("followed", followerService.getFollowedPlaylists());

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


    @GetMapping("/lectors/addSongToStudent")
    public String addSongToStudent(@RequestParam("lectorId") Long theLectorId, @RequestParam("studentId") Long theStudentId, Model theModel) {
        User student = userService.getUserById(theStudentId);

        // create model attribute to bind form data
        Song theSong = new Song();
        theSong.setInstrument(InstrumentEnum.GUITAR);
        theSong.setTuning(Tuning.TuningEnum.NONE);
        StudentSongForm studentSongForm = new StudentSongForm(student, theSong);


        theModel.addAttribute("student", student);
        theModel.addAttribute("studentSongForm", studentSongForm);
        theModel.addAttribute("instruments", InstrumentEnum.values());
        theModel.addAttribute("tunings", Tuning.TuningEnum.values());

        return "lector-song-form";
    }

    @GetMapping("/lectors/assignedSongs")
    public String getAssignedSongs(
            @RequestParam("lectorId") Long lectorId,
            @RequestParam("studentId") Long studentId,
            Model theModel
    ) {
        User student = userService.getUserById(studentId);
        User lector = userService.getUserById(lectorId);
        User user = UserUtil.getCurrentUser(userService);
        var songs = songService.getSongsAssignedByLector(student, lector);

        theModel.addAttribute("student", student);
        theModel.addAttribute("songs", songs);
        theModel.addAttribute("user", user);

        return "lector-assigned-songs";
    }

    @PostMapping("/lectors/saveStudentSong")
    public String saveStudentSong(
            @ModelAttribute("studentSongForm") StudentSongForm studentSongForm,
            @RequestParam("backing_track") MultipartFile backingTrack
    ) throws InvalidFileExtensionException, IOException, UnsupportedAudioFileException {
        User lector = UserUtil.getCurrentUser(userService);
        User formstudent = studentSongForm.getStudent();
        User student = userService.getUserById(formstudent.getId());
        Song theSong = studentSongForm.getSong();
        theSong.setAssignedBy(lector);



        // Set song user to student
        theSong.setUser(student);
        // Tuning - if instrument other the guitar or bass set to none
        if (theSong.getInstrument() != InstrumentEnum.GUITAR && theSong.getInstrument() != InstrumentEnum.BASS)
            theSong.setTuning(Tuning.TuningEnum.NONE);
        theSong = songService.save(theSong);

        var path = storageService.saveBackingTrack(backingTrack, theSong);
        theSong.setLength(SongsUtil.getSongDuration(path));
        theSong.setBackingTrackFilename(path.toString());
        songService.save(theSong);

        // Set song belong to student
        student.getSongs().add(theSong);
        userService.save(student);

        // Add song to student's lector playlist
        // Get lector-student playlist

        return "redirect:/lectors";
    }
}
