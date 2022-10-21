package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.entity.Instrument;
import java.util.List;

public interface SongDao {

    Song findByTitleArtistUser(String title, String artist, User user);

    Song getSongById(Long songId);

    List<Song> getSongsByUser(User user);

    List<Song> getSongsByUserInstrument(User user, Instrument instrument);

    void save(Song song);

    void delete(Long songId);
}