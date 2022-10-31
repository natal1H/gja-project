package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.PlaylistDao;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.SongsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistDao playlistDao;

    public PlaylistServiceImpl(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    @Override
    @Transactional
    public Playlist getPlaylistByUserByName(User user, String playlist_name) {
        return playlistDao.getPlaylistByUserByName(user, playlist_name);
    }

    @Override
    @Transactional
    public Playlist getPlaylistById(Long id) {
        return playlistDao.getPlaylistById(id);
    }

    @Override
    @Transactional
    public List<Playlist> getPlaylistsByUser(User user) {
        return playlistDao.getPlaylistsByUser(user);
    }

    @Override
    @Transactional
    public List<Playlist> getPlaylistsByUserInstrument(User user, InstrumentEnum instrument) {
        return playlistDao.getPlaylistsByUserInstrument(user, instrument);
    }

    @Override
    @Transactional
    public void save(Playlist playlist) {
        playlistDao.save(playlist);
    }

    @Override
    @Transactional
    public void delete(Long playlistId) {
        playlistDao.deletePlaylistById(playlistId);
    }

    @Override
    @Transactional
    public void deleteSongFromPlaylist(Playlist playlist, Song song) {
        playlist.getSongs().remove(song);
        playlistDao.save(playlist);
    }

    @Override
    @Transactional
    public void addSongToPlaylist(Playlist playlist, Song song) {
        if(!(playlist.getSongs().contains(song))) {
            playlist.getSongs().add(song);
            playlistDao.save(playlist);
        }
    }

    @Override
    public List<Song> getSortedPlaylistsSongsByOption(Playlist playlist, String sortStr) {
        List<Song> allSongs = playlist.getSongs();

        allSongs.sort(Comparator.comparing(Song::getArtist));

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
