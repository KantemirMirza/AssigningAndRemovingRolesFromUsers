package com.example.assigningandremovingrolesfromusers.services.impl;

import com.example.assigningandremovingrolesfromusers.entities.Role;
import com.example.assigningandremovingrolesfromusers.entities.User;
import com.example.assigningandremovingrolesfromusers.exception.UserAlreadyExistException;
import com.example.assigningandremovingrolesfromusers.repositories.IRoleRepository;
import com.example.assigningandremovingrolesfromusers.repositories.IUserRepository;
import com.example.assigningandremovingrolesfromusers.services.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;

    @Override
    public User registerUser(User user) throws UserAlreadyExistException {
        if(userRepository.existUserEmail(user.getEmail())){
            throw new UserAlreadyExistException(user.getEmail() + "already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("User_ROLE").get();
        user.setRoles(Collections.singleton(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUserByEmail(String email) {
        User theUser = getUserByEmail(email);
        if(theUser != null){
            userRepository.deleteByUserEmail(email);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) userRepository.findByUserEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }
}
