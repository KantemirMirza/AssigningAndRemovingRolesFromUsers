package com.example.assigningandremovingrolesfromusers.exception;

public class UserAlreadyExistException extends Throwable {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
