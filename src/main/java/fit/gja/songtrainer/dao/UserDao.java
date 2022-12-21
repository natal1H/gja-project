package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserDao extends JpaRepository<User, Long> {

    User getUserById(Long id);
    User findByUserName(String userName);

    @Query("SELECT u FROM User u WHERE u.userName LIKE %?1%"
            + " OR u.firstName LIKE %?1%"
            + " OR u.lastName LIKE %?1%")
    List<User> searchUserByName(String keyword);
}
