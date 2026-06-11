package org.example.entity;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public boolean isAdmin(){
        return currentUser.getRole() == Role.ADMIN;
    }

    public boolean isHotelManager(){
        return currentUser.getRole() == Role.HOTEL_MANAGER;
    }

    public void clear() {
        this.currentUser = null;
    }
}
