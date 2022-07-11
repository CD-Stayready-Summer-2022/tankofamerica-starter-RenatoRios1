package com.codedifferently.tankofamerica.user.serviceandrepo;

import com.codedifferently.tankofamerica.user.exceptions.CurrentUserNotLoggedInException;
import com.codedifferently.tankofamerica.user.modelclasses.User;

public interface UserService {
    User create(User user);
    Iterable<User> getAllUsers();
    User getById(Long id);
    User getByUsername(String username);
    Boolean exists(String email);
    Boolean validateUserPassword(User user, String password);
    Boolean isValidName(String name);
    Boolean isValidPassword(String password);
    Boolean isValidEmail(String str);
    void setCurrentUser(String username);
    User getCurrentUser() throws CurrentUserNotLoggedInException;
    void signOutCurrentUser();
}
