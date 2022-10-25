package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleDao extends JpaRepository<Role,Long> {
    public Role findRoleByName(String theRoleName);
}
