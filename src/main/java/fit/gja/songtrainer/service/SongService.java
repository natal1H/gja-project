package fit.gja.songtrainer.service;

import fit.gja.songtrainer.entity.Instrument;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import org.springframework.stereotype.Service;
import java.util.List;

public interface SongService {

    Song getSongById(Long id);

    List<Song> getSongsByUser(User user);

    List<Song> getSongsByUserInstrument(User user, Instrument instrument);

    void save(Song theSong);

    void delete(Long songId);
}
