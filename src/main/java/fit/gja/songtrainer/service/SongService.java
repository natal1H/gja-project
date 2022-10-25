package fit.gja.songtrainer.service;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;

import java.util.List;

public interface SongService {

    Song getSongById(Long id);

    List<Song> getSongsByUser(User user);

    List<Song> getSongsByUserInstrument(User user, InstrumentEnum instrument);

    void save(Song theSong);

    void delete(Long songId);
}
