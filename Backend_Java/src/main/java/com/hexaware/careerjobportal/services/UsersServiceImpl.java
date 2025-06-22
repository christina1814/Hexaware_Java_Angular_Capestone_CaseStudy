package com.hexaware.careerjobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careerjobportal.entities.Users;
import com.hexaware.careerjobportal.repositories.UserRepository;

@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
