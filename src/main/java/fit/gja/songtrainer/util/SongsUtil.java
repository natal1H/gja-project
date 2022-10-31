package fit.gja.songtrainer.util;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.SongService;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SongsUtil {

    public static List<Song> getUsersSongsSorted(SongService songService, User u, String sortStr) {
        List<Song> theSongs = null;

        // get user's songs in order specified by sort
        switch (sortStr) {
            case "ArtistASC":
                theSongs = songService.findByUserOrderByArtistAsc(u);
                break;
            case "ArtistDESC":
                theSongs = songService.findByUserOrderByArtistDesc(u);
                break;
            case "TitleASC":
                theSongs = songService.findByUserOrderByTitleAsc(u);
                break;
            case "TitleDESC":
                theSongs = songService.findByUserOrderByTitleDesc(u);
                break;
            case "Tuning":
                theSongs = songService.findByUserOrderByTuningAsc(u);
                break;
            case "LengthASC":
                theSongs = songService.findByUserOrderByLengthAsc(u);
                break;
            case "LengthDESC":
                theSongs = songService.findByUserOrderByLengthDesc(u);
                break;
            case "TimesPlayedASC":
                theSongs = songService.findByUserOrderByTimes_playedAsc(u);
                break;
            case "TimesPlayedDESC":
                theSongs = songService.findByUserOrderByTimes_playedDesc(u);
                break;
            case "LastPlayedASC":
                theSongs = songService.findByUserOrderByLast_playedAsc(u);
                break;
            case "LastPlayedDESC":
                theSongs = songService.findByUserOrderByLast_playedDesc(u);
                break;
            default:
                theSongs = songService.getSongsByUser(u);
        }
        return theSongs;
    }

    public static List<Song> filterSongsByInstrument(List<Song> theSongs, String instrumentStr) {
        if (!instrumentStr.equals("ALL")) {
            // creating a Predicate condition checking for non instrument songs
            Predicate<Song> isNotGuitar = item -> item.getInstrument() == Instrument.InstrumentEnum.valueOf(instrumentStr);
            theSongs = theSongs.stream().filter(isNotGuitar).collect(Collectors.toList());
        }
        return theSongs;
    }
}
