package fit.gja.songtrainer.util;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.SongService;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class with song relate functions
 */
public class SongsUtil {

    /**
     * Filter songs based on instrument
     * @param theSongs list of songs
     * @param instrumentStr string representations of selected instrument
     * @return list of songs with specified instrument
     */
    public static List<Song> filterSongsByInstrument(List<Song> theSongs, String instrumentStr) {
        if (!instrumentStr.equals("ALL")) {
            // creating a Predicate condition checking for non instrument songs
            Predicate<Song> isNotGuitar = item -> item.getInstrument() == InstrumentEnum.valueOf(instrumentStr);
            theSongs = theSongs.stream().filter(isNotGuitar).collect(Collectors.toList());
        }
        return theSongs;
    }

    /**
     * Filter songs by visibility
     * @param theSongs list of songs
     * @return only the songs visible to public
     */
    public static List<Song> filterSongsByVisible(List<Song> theSongs) {
        Predicate<Song> isNotVisible = item -> !item.getVisible();
        theSongs = theSongs.stream().filter(isNotVisible).collect(Collectors.toList());
        return theSongs;
    }



    /**
     * Returns backing track duration in seconds
     *
     * @param path path to backing track file
     * @return Duration of audio file in seconds
     * @throws UnsupportedAudioFileException when audio file is not supported
     * @throws IOException                   on IO error
     */
    public static int getSongDuration(Path path) throws UnsupportedAudioFileException, IOException {
        var fileFormat = AudioSystem.getAudioFileFormat(path.toFile());
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = fileFormat.properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            return (int) (microseconds / 1000_000);
        } else {
            var stream = AudioSystem.getAudioInputStream(path.toFile());
            var format = stream.getFormat();
            var frames = stream.getFrameLength();
            Float duration = frames / format.getFrameRate();
            return duration.intValue();
        }
    }
}
