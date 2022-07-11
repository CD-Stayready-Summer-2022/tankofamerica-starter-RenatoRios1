package com.codedifferently.tankofamerica.user.modelclasses;

import com.codedifferently.tankofamerica.user.modelclasses.User;

public class CurrentUser {

    public static User currentUser;

    public CurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
