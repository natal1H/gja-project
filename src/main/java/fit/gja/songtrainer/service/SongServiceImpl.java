package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.SongDao;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongDao songDao;

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
    public void save(Song theSong) {
        songDao.save(theSong);
    }

    @Override
    @Transactional
    public void delete(Long songId) {
        songDao.delete(songId);
    }
}
