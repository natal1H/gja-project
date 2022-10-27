package fit.gja.songtrainer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        reason = "File extension is not supported"
)
public class InvalidFileExtensionException extends Exception{
}
