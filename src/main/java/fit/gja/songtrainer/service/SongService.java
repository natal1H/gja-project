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
}
