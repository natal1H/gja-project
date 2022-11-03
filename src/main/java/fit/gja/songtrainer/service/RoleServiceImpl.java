package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.RoleDao;
import fit.gja.songtrainer.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public Role findRoleByName(String theRoleName) {
        return roleDao.findRoleByName(theRoleName);
    }
}
