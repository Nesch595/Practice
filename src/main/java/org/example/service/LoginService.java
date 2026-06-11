package org.example.service;

import org.example.entity.User;
import org.example.entity.UserContext;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserContext userContext;

    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserContext userContext) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userContext = userContext;
    }

    public boolean login(String username, String password) {
        if (userContext.isAuthenticated()) {
            System.out.println("Already logged in as: " + userContext.getCurrentUser().getUsername());
            System.out.println("Please logout first");
            return false;
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isDeleted()) {
            System.out.println("Account is deactivated");
            return false;
        }

        if (passwordEncoder.matches(password, user.getPasswordHash())) {
            userContext.setCurrentUser(user);
            System.out.println("Welcome, " + user.getUsername() + "!");
            System.out.println("Your role: " + user.getRole());
            return true;
        } else {
            System.out.println("Invalid password");
            return false;
        }
    }
}
