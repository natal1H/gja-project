package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.SongDao;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongDao songDao;

    @Autowired
    private PlaylistService playlistService;

    @Override
    @Transactional
    public Song getSongById(Long id) {
        return songDao.getSongById(id);
    }

    @Override
    @Transactional
    public List<Song> getSongsByUser(User user) {
        return songDao.getSongsByUser(user);
    }

    @Override
    @Transactional
    public List<Song> getSongsByUserInstrument(User user, InstrumentEnum instrument) {
        return songDao.getSongsByUserInstrument(user, instrument);
    }

    @Override
    @Transactional
    public void deletePlaylistFromSong(Song song, Playlist playlist) {
        song.getPlaylists().remove(playlist);
        songDao.save(song);
    }

    @Override
    @Transactional
    public void save(Song theSong) {
        songDao.save(theSong);
    }

    @Override
    @Transactional
    public void delete(Long songId) {
        Song theSong = this.getSongById(songId);
        for (Playlist tempPlaylist: theSong.getPlaylists()) {
            playlistService.deleteSongFromPlaylist(tempPlaylist, theSong);
        }
        List<Playlist> allPlaylists = theSong.getPlaylists();
        theSong.getPlaylists().removeAll(allPlaylists);
        songDao.deleteSongById(songId);
    }
}
