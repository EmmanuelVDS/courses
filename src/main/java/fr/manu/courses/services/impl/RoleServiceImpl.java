package fr.manu.courses.services.impl;

import fr.manu.courses.dao.RoleDao;
import fr.manu.courses.models.Role;
import fr.manu.courses.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public Role addRole(String role) {
        return roleDao.save(new Role(role));
    }

    @Override
    public Role getRole(String role) {
        return roleDao.findFirstByRoleLike("%" + role + "%");
    }

    @Override
    public List<Role> getRoleList() {
        return roleDao.findAll();
    }

    @Override
    public Role getRoleByRole(String role) {
        return roleDao.getRoleByRole(role);
    }
}
