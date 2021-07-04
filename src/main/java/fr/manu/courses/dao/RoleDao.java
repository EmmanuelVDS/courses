package fr.manu.courses.dao;

import fr.manu.courses.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findFirstByRoleLike(String nom);

    Role getRoleByRole(String role);
}
