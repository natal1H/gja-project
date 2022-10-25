package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    User findByUserName(String userName);
}
