package fit.gja.songtrainer.controller;

import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.exceptions.InvalidFileExtensionException;
import fit.gja.songtrainer.exceptions.NoProfilePictureException;
import fit.gja.songtrainer.exceptions.UserNotFoundException;
import fit.gja.songtrainer.service.*;
import fit.gja.songtrainer.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class ProfilePictureController {
    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/profilePicture")
    public void uploadProfilePicture(@RequestParam(value = "picture") MultipartFile picture) throws UserNotFoundException, InvalidFileExtensionException, IOException {
        User user = UserUtil.getCurrentUser(userService);
        if(user == null) throw new UserNotFoundException();
        Path savedPath = storageService.saveProfilePicture(picture, user);
        user.setProfilePicturePath(savedPath.toString());
        userService.save(user);
    }

    @GetMapping("/profilePicture")
    public ResponseEntity<FileSystemResource> getBackingTrack() throws IOException, UserNotFoundException, NoProfilePictureException {
        User user = UserUtil.getCurrentUser(userService);
        if(user == null) throw new UserNotFoundException();
        File picture = storageService.loadProfilePicture(user);
        if(picture == null) throw new NoProfilePictureException();
        String contentType = Files.probeContentType(picture.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        return new ResponseEntity<>(new FileSystemResource(picture), headers, HttpStatus.OK);
    }

    @DeleteMapping("/profilePicture")
    public void deleteProfilePicture() throws UserNotFoundException {
        User user = UserUtil.getCurrentUser(userService);
        if(user == null) throw new UserNotFoundException();
        storageService.removeProfilePicture(user);
    }
}
