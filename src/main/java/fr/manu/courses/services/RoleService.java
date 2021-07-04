package fr.manu.courses.services;

import fr.manu.courses.models.Role;

import java.util.List;

public interface RoleService {
    Role addRole(String role);

    Role getRole(String role);

    List<Role> getRoleList();

    Role getRoleByRole(String role);
}
