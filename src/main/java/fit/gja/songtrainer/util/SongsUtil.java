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

    // TODO: change this to read from db in UserDao
    public static List<Song> filterSongsByVisible(List<Song> theSongs) {
        Predicate<Song> isNotVisible = item -> !item.getVisible();
        theSongs = theSongs.stream().filter(isNotVisible).collect(Collectors.toList());
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

    public static List<Song> sortSongS(List<Song> allSongs, String sortStr) {
        // for sorting by last_played - todo: refactor
        List<Song> playedSongs = SongsUtil.getPlayedFromList(allSongs);
        List<Song> neverPlayedSongs = SongsUtil.getNeverPlayedFromList(allSongs);

        // get user's songs in order specified by sort
        switch (sortStr) {
            case "ArtistASC" -> allSongs.sort(Comparator.comparing(Song::getArtist));
            case "ArtistDESC" -> {
                allSongs.sort(Comparator.comparing(Song::getArtist));
                Collections.reverse(allSongs);
            }
            case "TitleASC" -> allSongs.sort(Comparator.comparing(Song::getTitle));
            case "TitleDESC" -> {
                allSongs.sort(Comparator.comparing(Song::getTitle));
                Collections.reverse(allSongs);
            }
            case "Tuning" -> allSongs.sort(Comparator.comparing(Song::getTuning));
            case "LengthASC" -> allSongs.sort(Comparator.comparing(Song::getLength));
            case "LengthDESC" -> {
                allSongs.sort(Comparator.comparing(Song::getLength));
                Collections.reverse(allSongs);
            }
            case "TimesPlayedASC" -> allSongs.sort(Comparator.comparing(Song::getTimes_played));
            case "TimesPlayedDESC" -> {
                allSongs.sort(Comparator.comparing(Song::getTimes_played));
                Collections.reverse(allSongs);
            }
            case "LastPlayedASC" -> { // by ascending means Never first - last most recently played
                playedSongs.sort(Comparator.comparing(Song::getLast_played));
                allSongs = Stream.concat(neverPlayedSongs.stream(), playedSongs.stream()).toList();
            }
            case "LastPlayedDESC" -> { // by ascending means Never first - last most recently played
                playedSongs.sort(Comparator.comparing(Song::getLast_played));
                Collections.reverse(playedSongs);
                allSongs = Stream.concat(playedSongs.stream(), neverPlayedSongs.stream()).toList();
            }
        }

        return allSongs;
    }
}
