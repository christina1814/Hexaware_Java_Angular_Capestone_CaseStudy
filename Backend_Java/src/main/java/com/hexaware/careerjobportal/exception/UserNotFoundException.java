package com.hexaware.careerjobportal.exception;

public class UserNotFoundException extends RuntimeException 
{
    public UserNotFoundException(String message) {
        super(message);
    }
}