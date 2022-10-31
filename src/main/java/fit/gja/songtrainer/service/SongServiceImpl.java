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

    private final SongDao songDao;

    private final PlaylistService playlistService;

    public SongServiceImpl(SongDao songDao, PlaylistService playlistService) {
        this.songDao = songDao;
        this.playlistService = playlistService;
    }

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

    // Different song sortings
    @Override
    @Transactional
    public List<Song> findByUserOrderByArtistAsc(User user) {
        return songDao.findByUserOrderByArtistAsc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByArtistDesc(User user) {
        return songDao.findByUserOrderByArtistDesc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByTitleAsc(User user) {
        return songDao.findByUserOrderByTitleAsc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByTitleDesc(User user) {
        return songDao.findByUserOrderByTitleDesc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByTuningAsc(User user) {
        return songDao.findByUserOrderByTuningAsc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByLengthAsc(User user) {
        return songDao.findByUserOrderByLengthAsc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByLengthDesc(User user) {
        return songDao.findByUserOrderByLengthDesc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByTimes_playedAsc(User user) {
        return songDao.findByUserOrderByTimes_playedAsc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByTimes_playedDesc(User user) {
        return songDao.findByUserOrderByTimes_playedDesc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByLast_playedAsc(User user) {
        return songDao.findByUserOrderByLast_playedAsc(user);
    }

    @Override
    @Transactional
    public List<Song> findByUserOrderByLast_playedDesc(User user) {
        return songDao.findByUserOrderByLast_playedDesc(user);
    }
}
