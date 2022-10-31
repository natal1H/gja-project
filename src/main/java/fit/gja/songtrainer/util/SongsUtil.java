package fit.gja.songtrainer.util;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.service.SongService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SongsUtil {

    public static List<Song> getUsersSongsSorted(SongService songService, User u, String sortStr) {

        // get user's songs in order specified by sort
        return switch (sortStr) {
            case "ArtistASC" -> songService.findByUserOrderByArtistAsc(u);
            case "ArtistDESC" -> songService.findByUserOrderByArtistDesc(u);
            case "TitleASC" -> songService.findByUserOrderByTitleAsc(u);
            case "TitleDESC" -> songService.findByUserOrderByTitleDesc(u);
            case "Tuning" -> songService.findByUserOrderByTuningAsc(u);
            case "LengthASC" -> songService.findByUserOrderByLengthAsc(u);
            case "LengthDESC" -> songService.findByUserOrderByLengthDesc(u);
            case "TimesPlayedASC" -> songService.findByUserOrderByTimes_playedAsc(u);
            case "TimesPlayedDESC" -> songService.findByUserOrderByTimes_playedDesc(u);
            case "LastPlayedASC" -> songService.findByUserOrderByLast_playedAsc(u);
            case "LastPlayedDESC" -> songService.findByUserOrderByLast_playedDesc(u);
            default -> songService.getSongsByUser(u);
        };
    }

    public static List<Song> filterSongsByInstrument(List<Song> theSongs, String instrumentStr) {
        if (!instrumentStr.equals("ALL")) {
            // creating a Predicate condition checking for non instrument songs
            Predicate<Song> isNotGuitar = item -> item.getInstrument() == Instrument.InstrumentEnum.valueOf(instrumentStr);
            theSongs = theSongs.stream().filter(isNotGuitar).collect(Collectors.toList());
        }
        return theSongs;
    }

    public static List<Song> getNeverPlayedFromList(List<Song> theSongs) {
        List<Song> neverPlayedSongs = new ArrayList<Song>();
        for (Song tempSong : theSongs) {
            if (tempSong.getLast_played() == null) {
                neverPlayedSongs.add(tempSong);
            }
        }
        return neverPlayedSongs;
    }

    public static List<Song> getPlayedFromList(List<Song> theSongs) {
        List<Song> playedSongs = new ArrayList<Song>();
        for (Song tempSong : theSongs) {
            if (tempSong.getLast_played() != null) {
                playedSongs.add(tempSong);
            }
        }
        return playedSongs;
    }
}
