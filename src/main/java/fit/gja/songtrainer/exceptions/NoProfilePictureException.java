package fit.gja.songtrainer.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        reason = "User has no profile picture"
)
public class NoProfilePictureException extends Exception {
}
