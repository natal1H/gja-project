package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.PlaylistDao;
import fit.gja.songtrainer.entity.Instrument;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Playlist> getPlaylistsByUserInstrument(User user, Instrument instrument) {
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
        playlistDao.delete(playlistId);
    }
}
