package fit.gja.songtrainer.util;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.SongService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SongsUtil {

    public static List<Song> filterSongsByInstrument(List<Song> theSongs, String instrumentStr) {
        if (!instrumentStr.equals("ALL")) {
            // creating a Predicate condition checking for non instrument songs
            Predicate<Song> isNotGuitar = item -> item.getInstrument() == InstrumentEnum.valueOf(instrumentStr);
            theSongs = theSongs.stream().filter(isNotGuitar).collect(Collectors.toList());
        }
        return theSongs;
    }

    // TODO: change this to read from db in UserDao
    public static List<Song> filterSongsByVisible(List<Song> theSongs) {
        Predicate<Song> isNotVisible = item -> !item.getVisible();
        theSongs = theSongs.stream().filter(isNotVisible).collect(Collectors.toList());
        return theSongs;
    }
}
