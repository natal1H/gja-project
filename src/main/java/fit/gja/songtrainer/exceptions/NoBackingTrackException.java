package fit.gja.songtrainer.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        reason = "Song has no backing track"
)
public class NoBackingTrackException extends Exception {
}
