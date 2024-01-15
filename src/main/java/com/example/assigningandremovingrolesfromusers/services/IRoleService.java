package com.example.assigningandremovingrolesfromusers.services;

import com.example.assigningandremovingrolesfromusers.entities.Role;
import com.example.assigningandremovingrolesfromusers.entities.User;
import com.example.assigningandremovingrolesfromusers.exception.UserAlreadyExistException;

import java.util.List;

public interface IRoleService {

    List<Role> getAllRolesFromDB();

    Role saveRoleByIdToDB(Role newRole);

    void deleteRoleByIdFromDB(Long roleId);

    Role getRoleByNameFromDB(String roleName);

    Role getRoleByIdFromDB(Long roleId);

    User removeUserFromRole(Long userId, Long roleId);

    User assignUserToRole(Long userId, Long roleId) throws UserAlreadyExistException;

    Role removeAllUserFromRole(Long roleId);
}
