package com.hexaware.careerjobportal.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.careerjobportal.entities.Users;
import com.hexaware.careerjobportal.services.IUsersService;

@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    @Autowired
    private IUsersService userService;

    // Get user details by email
    @GetMapping("/by-email") //done
    public Users getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }
}
