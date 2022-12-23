package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.entity.UserHasLectors;
import fit.gja.songtrainer.entity.UserHasLectorsPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasLectorDao extends JpaRepository<UserHasLectors, UserHasLectorsPk> {
    UserHasLectors findByUserAndPlaylistId(User user, Long playlistId);
}
