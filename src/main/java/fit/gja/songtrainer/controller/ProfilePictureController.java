package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.dao.UserDao;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import fit.gja.songtrainer.exceptions.NoProfilePictureException;
import fit.gja.songtrainer.exceptions.UserNotFoundException;
import fit.gja.songtrainer.service.*;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Profile picture handling
 */
@RestController
public class ProfilePictureController {
    private final UserService userService;

    private final StorageService storageService;
    private final UserDao userDao;

    public ProfilePictureController(UserService userService, StorageService storageService, UserDao userDao) {
        this.userService = userService;
        this.storageService = storageService;
        this.userDao = userDao;
    }

    /**
     * Sets new profile picture for current user
     * @param picture new profile picture
     * @throws UserNotFoundException current user does not exist
     * @throws InvalidFileExtensionException trying to upload unsupported file extension
     * @throws IOException IO error
     */
    @PostMapping("/profilePicture")
    public void uploadProfilePicture(@RequestParam(value = "picture") MultipartFile picture) throws UserNotFoundException, InvalidFileExtensionException, IOException {
        User user = UserUtil.getCurrentUser(userService);
        if(user == null) throw new UserNotFoundException();
        Path savedPath = storageService.saveProfilePicture(picture, user);
        user.setProfilePicturePath(savedPath.toString());
        userService.save(user);
    }

    /**
     * Gets profile picture for specific user. If user id is not specified returns current user profile picture
     * @param userId profile picture for user with this id
     * @return profile picture
     * @throws IOException on IO error
     * @throws UserNotFoundException when userId was not found
     * @throws NoProfilePictureException when specified user does not have profile picture
     */
    @GetMapping("/profilePicture")
    public ResponseEntity<FileSystemResource> getProfilePicture(@RequestParam(value = "userId", required = false) Long userId) throws IOException, UserNotFoundException, NoProfilePictureException {
        User user = null;
        if(userId == null)
            user = UserUtil.getCurrentUser(userService);
        else
            user = userDao.getUserById(userId);
        if(user == null) throw new UserNotFoundException();

        File picture = storageService.loadProfilePicture(user);
        if(picture == null) throw new NoProfilePictureException();
        String contentType = Files.probeContentType(picture.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        return new ResponseEntity<>(new FileSystemResource(picture), headers, HttpStatus.OK);
    }

    /**
     * Removes profile picture for current user
     * @throws UserNotFoundException current user was not found
     */
    @DeleteMapping("/profilePicture")
    public void deleteProfilePicture() throws UserNotFoundException {
        User user = UserUtil.getCurrentUser(userService);
        if(user == null) throw new UserNotFoundException();
        storageService.removeProfilePicture(user);
        user.setProfilePicturePath(null);
        userService.save(user);
    }
}
