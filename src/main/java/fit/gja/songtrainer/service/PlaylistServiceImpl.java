package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.PlaylistDao;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistDao playlistDao;

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

        // get user's songs in order specified by sort
        switch (sortStr) {
            case "ArtistASC":
                allSongs.sort(Comparator.comparing(Song::getArtist));
                break;
            case "ArtistDESC":
                allSongs.sort(Comparator.comparing(Song::getArtist));
                Collections.reverse(allSongs);
                break;
            case "TitleASC":
                allSongs.sort(Comparator.comparing(Song::getTitle));
                break;
            case "TitleDESC":
                allSongs.sort(Comparator.comparing(Song::getTitle));
                Collections.reverse(allSongs);
                break;
            case "Tuning":
                allSongs.sort(Comparator.comparing(Song::getTuning));
                break;
            case "LengthASC":
                allSongs.sort(Comparator.comparing(Song::getLength));
                break;
            case "LengthDESC":
                allSongs.sort(Comparator.comparing(Song::getLength));
                Collections.reverse(allSongs);
                break;
            case "TimesPlayedASC":
                allSongs.sort(Comparator.comparing(Song::getTimes_played));
                break;
            case "TimesPlayedDESC":
                allSongs.sort(Comparator.comparing(Song::getTimes_played));
                Collections.reverse(allSongs);
                break;
        }

        return allSongs;
    }

}
