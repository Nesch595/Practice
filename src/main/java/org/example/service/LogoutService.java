package org.example.service;

import org.example.entity.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    private final UserContext userContext;

    @Autowired
    public LogoutService(UserContext userContext) {
        this.userContext = userContext;
    }

    public void logout() {
        if (userContext.getCurrentUser() != null) {
            System.out.println("Goodbye, " + userContext.getCurrentUser().getUsername());
            userContext.clear();
        } else System.out.println("No user is logged in");
    }
}
