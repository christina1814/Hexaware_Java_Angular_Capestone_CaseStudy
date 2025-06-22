package com.hexaware.careerjobportal.services;

import com.hexaware.careerjobportal.dto.UserRegistrationDTO;
import com.hexaware.careerjobportal.entities.Users;

public interface IUsersService {
    Users getUserByEmail(String email);
}
