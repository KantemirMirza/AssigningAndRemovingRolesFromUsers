package com.example.assigningandremovingrolesfromusers.exception;

public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException(String message) {
        super(message);
    }
}
