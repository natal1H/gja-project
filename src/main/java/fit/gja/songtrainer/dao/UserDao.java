package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User user);
}
