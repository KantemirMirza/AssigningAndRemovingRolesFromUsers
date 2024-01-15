package com.example.assigningandremovingrolesfromusers.services.impl;

import com.example.assigningandremovingrolesfromusers.entities.Role;
import com.example.assigningandremovingrolesfromusers.entities.User;
import com.example.assigningandremovingrolesfromusers.exception.RoleAlreadyExistException;
import com.example.assigningandremovingrolesfromusers.exception.UserAlreadyExistException;
import com.example.assigningandremovingrolesfromusers.repositories.IRoleRepository;
import com.example.assigningandremovingrolesfromusers.repositories.IUserRepository;
import com.example.assigningandremovingrolesfromusers.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    @Override
    public List<Role> getAllRolesFromDB() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveRoleByIdToDB(Role newRole) {
        Optional<Role> roleAlreadyExist = roleRepository.findByName(newRole.getRoleName());
        if(roleAlreadyExist.isPresent()){
            throw new RoleAlreadyExistException(newRole.getRoleName() + "Already Exist");
        }
        return roleRepository.save(newRole);
    }

    @Override
    public void deleteRoleByIdFromDB(Long roleId) {
        this.removeAllUserFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role getRoleByNameFromDB(String roleName) {
        return roleRepository.findByName(roleName).get();
    }

    @Override
    public Role getRoleByIdFromDB(Long roleId) {
        return roleRepository.findById(roleId).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("User nor found");
    }

    @Override
    public User assignUserToRole(Long userId, Long roleId) throws UserAlreadyExistException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistException(user.get().getFirstName()
                    + "is already assigned to the" + role.get().getRoleName());
        }
        role.ifPresent(theRole ->  theRole.assignUserToRole(user.get()));
        roleRepository.save(role.get());
        return user.get();
    }

    @Override
    public Role removeAllUserFromRole(Long roleId) {
        Optional<Role> removeRole = roleRepository.findById(roleId);
        removeRole.ifPresent(Role :: removeAllUsersFromRole);
        return roleRepository.save(removeRole.get());
    }
}
