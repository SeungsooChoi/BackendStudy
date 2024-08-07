package com.example.demo.global.util;

public class DuplicateUserException extends Throwable {
    public DuplicateUserException(String msg) {
        super(msg);
    }
}