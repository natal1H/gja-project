package fit.gja.songtrainer.service;

import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public interface SongService {

    Song getSongById(Long id);

    List<Song> getSongsByUser(User user);

    List<Song> getSongsByUserInstrument(User user, InstrumentEnum instrument);

    void deletePlaylistFromSong(Song song, Playlist playlist);

    void save(Song theSong);

    void delete(Long songId);

    // Different sortings
    List<Song> findByUserOrderByArtistAsc(User user);
    List<Song> findByUserOrderByArtistDesc(User user);
    List<Song> findByUserOrderByTitleAsc(User user);
    List<Song> findByUserOrderByTitleDesc(User user);
    List<Song> findByUserOrderByTuningAsc(User user);
    List<Song> findByUserOrderByLengthAsc(User user);
    List<Song> findByUserOrderByLengthDesc(User user);
    List<Song> findByUserOrderByTimes_playedAsc(User user);
    List<Song> findByUserOrderByTimes_playedDesc(User user);
    List<Song> findByUserOrderByLast_playedAsc(User user);
    List<Song> findByUserOrderByLast_playedDesc(User user);
}
