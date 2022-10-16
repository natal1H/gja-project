package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Role;

public interface RoleDao {
    public Role findRoleByName(String theRoleName);
}
