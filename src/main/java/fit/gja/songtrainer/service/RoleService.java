package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.RoleDao;
import fit.gja.songtrainer.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role findRoleByName(String theRoleName) {
        return roleDao.findRoleByName(theRoleName);
    }
}
