package com.hexaware.careerjobportal.dto;

public class AuthResponseDTO 
{
    private String token;
    private String role;
    private int userId;

    public AuthResponseDTO(String token, String role, int userId) 
    {
        this.token = token;
        this.role = role;
        this.userId = userId;
    }

    //Getters and Setters
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
