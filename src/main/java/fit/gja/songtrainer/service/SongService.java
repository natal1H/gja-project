package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.SongDao;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.InstrumentEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service for songs
 */
@Service
public class SongService {

    private final SongDao songDao;

    private final PlaylistService playlistService;

    public SongService(SongDao songDao, PlaylistService playlistService) {
        this.songDao = songDao;
        this.playlistService = playlistService;
    }

    @Transactional
    public Song getSongById(Long id) {
        return songDao.getSongById(id);
    }

    @Transactional
    public List<Song> getSongsByUser(User user) {
        return songDao.getSongsByUser(user);
    }

    @Transactional
    public List<Song> getSongsByUserInstrument(User user, InstrumentEnum instrument) {
        return songDao.getSongsByUserInstrument(user, instrument);
    }

    @Transactional
    public Song save(Song theSong) {
        return songDao.save(theSong);
    }

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
    @Transactional
    public List<Song> findByUserOrderByArtistAsc(User user) {
        return songDao.findByUserOrderByArtistAsc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByArtistDesc(User user) {
        return songDao.findByUserOrderByArtistDesc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByTitleAsc(User user) {
        return songDao.findByUserOrderByTitleAsc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByTitleDesc(User user) {
        return songDao.findByUserOrderByTitleDesc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByTuningAsc(User user) {
        return songDao.findByUserOrderByTuningAsc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByLengthAsc(User user) {
        return songDao.findByUserOrderByLengthAsc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByLengthDesc(User user) {
        return songDao.findByUserOrderByLengthDesc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByTimes_playedAsc(User user) {
        return songDao.findByUserOrderByTimes_playedAsc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByTimes_playedDesc(User user) {
        return songDao.findByUserOrderByTimes_playedDesc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByLast_playedAsc(User user) {
        return songDao.findByUserOrderByLast_playedAsc(user);
    }

    @Transactional
    public List<Song> findByUserOrderByLast_playedDesc(User user) {
        return songDao.findByUserOrderByLast_playedDesc(user);
    }

    public List<Song> getSongsAssignedByLector(User student, User lector) {
        return songDao.findSongByUserAndAssignedBy(student, lector);
    }
}
