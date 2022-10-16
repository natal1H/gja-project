package fit.gja.songtrainer.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.user.CrmUser;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void save(CrmUser crmUser);
}
