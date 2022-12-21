package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.PlaylistDao;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.SongsUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistDao playlistDao;

    public PlaylistService(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    @Transactional
    public Playlist getPlaylistByUserByName(User user, String playlist_name) {
        return playlistDao.getPlaylistByUserByName(user, playlist_name);
    }

    @Transactional
    public Playlist getPlaylistById(Long id) {
        return playlistDao.getPlaylistById(id);
    }

    @Transactional
    public List<Playlist> getPlaylistsByUser(User user) {
        return playlistDao.getPlaylistsByUser(user);
    }

    @Transactional
    public List<Playlist> getPlaylistsByUserInstrument(User user, InstrumentEnum instrument) {
        return playlistDao.getPlaylistsByUserInstrument(user, instrument);
    }

    @Transactional
    public void save(Playlist playlist) {
        playlistDao.save(playlist);
    }

    @Transactional
    public void delete(Long playlistId) {
        playlistDao.deletePlaylistById(playlistId);
    }

    @Transactional
    public void deleteSongFromPlaylist(Playlist playlist, Song song) {
        playlist.getSongs().remove(song);
        playlistDao.save(playlist);
    }

    @Transactional
    public void addSongToPlaylist(Playlist playlist, Song song) {
        if(!(playlist.getSongs().contains(song))) {
            playlist.getSongs().add(song);
            playlistDao.save(playlist);
        }
    }

    public List<Song> getSortedPlaylistsSongsByOption(Playlist playlist, String sortStr) {
        return SongsUtil.sortSongS(playlist.getSongs(), sortStr);
    }

}
