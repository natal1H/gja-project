package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.PlaylistDao;
import fit.gja.songtrainer.dao.SongDao;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.InstrumentEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for playlists
 */
@Service
public class PlaylistService {

    private final PlaylistDao playlistDao;
    private final SongDao songDao;

    public PlaylistService(PlaylistDao playlistDao, SongDao songDao) {
        this.playlistDao = playlistDao;
        this.songDao = songDao;
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
    public void delete(User user, Long playlistId) {
        playlistDao.deletePlaylistById(playlistId);
    }

    @Transactional
    public void deleteSongFromPlaylist(Playlist playlist, Song song) {
        playlist.getSongs().remove(song);
        playlistDao.save(playlist);
    }

    @Transactional
    public void addSongToPlaylist(Playlist playlist, Song song) {
        if (!(playlist.getSongs().contains(song))) {
            playlist.getSongs().add(song);
            playlistDao.save(playlist);
        }
    }

    public List<Playlist> getPublicPlaylistsForUser(User user) {
        return playlistDao.getPlaylistByUserAndIsPublicTrue(user);
    }

    @Transactional
    public void deletePlaylistFromSong(Song song, Playlist playlist) {
        song.getPlaylists().remove(playlist);
        songDao.save(song);
    }

    /**
     * Updates existing playlist or creates a new one if id does not exist
     * @param playlist playlist definition
     */
    public void updatePlaylist(Playlist playlist) {
        // try to see if playlist already exists:
        if (playlist.getId() != null) {
            // Find original playlist in db
            Playlist originalPlaylist = getPlaylistById(playlist.getId());

            // if different instrument now, remove song from playlists
            if (originalPlaylist.getInstrument() != playlist.getInstrument()) {
                for (Song tempSong : originalPlaylist.getSongs()) { // remove playlist from all songs it had
                    deletePlaylistFromSong(tempSong, originalPlaylist);
                }
                List<Song> allSongs = originalPlaylist.getSongs();
                originalPlaylist.getSongs().removeAll(allSongs);
            }
            originalPlaylist.setName(playlist.getName());
            originalPlaylist.setInstrument(playlist.getInstrument());
            originalPlaylist.setPublic(playlist.isPublic());

            save(originalPlaylist);
        } else {
            save(playlist);
        }
    }
}
