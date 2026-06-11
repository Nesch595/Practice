package org.example.service;

import org.example.dto.UserDto;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.entity.UserContext;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserContext userContext;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserContext userContext, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userContext = userContext;
        this.userMapper = userMapper;
    }

    public Page<UserDto> findAllUsersForAdmin(Pageable pageable) {
        if (!userContext.isAdmin()) {
            throw new SecurityException("Only admin can view all users");
        }
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    public void deleteUser(Long userId) {
        if (!userContext.isAdmin()) {
            throw new SecurityException("Only admin can delete users");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDeleted(true);
        userRepository.save(user);
        System.out.println("User " + user.getUsername() + " has been deactivated");
    }

    public void changeUserRole(Long userId, Role newRole) {
        if (!userContext.isAdmin()) {
            throw new SecurityException("Only admin can change roles");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);
        userRepository.save(user);
        System.out.println("Role changed for " + user.getUsername() + " to " + newRole);
    }
}
