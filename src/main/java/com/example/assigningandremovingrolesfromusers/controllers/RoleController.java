package com.example.assigningandremovingrolesfromusers.controllers;

import com.example.assigningandremovingrolesfromusers.entities.Role;
import com.example.assigningandremovingrolesfromusers.entities.User;
import com.example.assigningandremovingrolesfromusers.exception.UserAlreadyExistException;
import com.example.assigningandremovingrolesfromusers.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRolesFromDB(), FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Role> saveRole(@RequestBody Role newRole){
        return new ResponseEntity<>(roleService.saveRoleByIdToDB(newRole), CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRole(@PathVariable("id") Long roleId ){
        roleService.deleteRoleByIdFromDB(roleId);
    }

    @PostMapping("/remove-all-users-from-role/{id}")
    public Role removeAllUsersFromRole(@PathVariable("id") Long roleId){
        return roleService.removeAllUserFromRole(roleId);
    }

    @PostMapping("/remove-user-from-role")
    public User removeUserFromRole(@RequestParam("userId") Long userId,
                                   @RequestParam("roleId") Long roleId){
        return roleService.removeUserFromRole(userId, roleId);
    }

    @PostMapping("/assign-user-to-role")
    public User assignUserToRole(@RequestParam("userId") Long userId,
                                   @RequestParam("roleId") Long roleId) throws UserAlreadyExistException {
        return roleService.assignUserToRole(userId, roleId);
    }


}
