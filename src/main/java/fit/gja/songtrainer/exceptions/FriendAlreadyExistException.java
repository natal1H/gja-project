package fit.gja.songtrainer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        reason = "Friend you are trying to add already exists"
)
public class FriendAlreadyExistException extends Exception {
}
