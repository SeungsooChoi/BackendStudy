package com.example.demo.global.util;

public class NonExistentUserException extends UserLoginException {
    public NonExistentUserException(String message) {
        super(message);
    }
}