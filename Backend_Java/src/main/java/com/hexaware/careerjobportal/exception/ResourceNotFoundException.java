package com.hexaware.careerjobportal.exception;

public class ResourceNotFoundException extends RuntimeException 
{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
