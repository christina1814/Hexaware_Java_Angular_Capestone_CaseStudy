package com.hexaware.careerjobportal.exception;

public class EmailAlreadyExistsException extends RuntimeException 
{
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
