package fit.gja.songtrainer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        reason = "User already followed"
)
public class AlreadyFollowingException extends Exception {
}
