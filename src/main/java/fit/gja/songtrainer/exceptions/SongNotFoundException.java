package fit.gja.songtrainer.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        reason = "Requested song was not found"
)
public class SongNotFoundException extends Exception{
}
