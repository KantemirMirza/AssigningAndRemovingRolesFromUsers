package com.example.assigningandremovingrolesfromusers.services;

import com.example.assigningandremovingrolesfromusers.entities.User;
import com.example.assigningandremovingrolesfromusers.exception.UserAlreadyExistException;

import java.util.List;

public interface IUserService {

    User registerUser(User user) throws UserAlreadyExistException;

    List<User> getAllUsers();

    void deleteUserByEmail(String email);

    User getUserByEmail(String email);
}
