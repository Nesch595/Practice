package org.example.service;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerCustomer(String username, String email, String rawPassword) {
        userRepository.findByUsername(username).ifPresent(_ -> {
            throw new RuntimeException("Username already exists");});
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = User.builder().username(username).email(email).passwordHash(encodedPassword).role(Role.CUSTOMER).build();
        userRepository.save(user);
        System.out.println("User is registered. Please, login now");
    }
}
