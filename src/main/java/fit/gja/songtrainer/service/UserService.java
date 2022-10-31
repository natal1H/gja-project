package fit.gja.songtrainer.service;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import org.springframework.security.core.userdetails.UserDetailsService;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.user.CrmUser;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    User getUserById(Long id);

    void save(CrmUser crmUser);

    //List<Song> getUsersSongsByInstrument(Long id, InstrumentEnum instrument);
}
