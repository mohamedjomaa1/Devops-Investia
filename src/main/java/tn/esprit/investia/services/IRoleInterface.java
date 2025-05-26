package tn.esprit.investia.services;

import tn.esprit.investia.entities.Role;

import java.util.List;

public interface IRoleInterface {
    Role addRole(Role role);

    List<Role> getRole();

    void deleteRole(Long id);

    Role updateRole(Role role);
}
