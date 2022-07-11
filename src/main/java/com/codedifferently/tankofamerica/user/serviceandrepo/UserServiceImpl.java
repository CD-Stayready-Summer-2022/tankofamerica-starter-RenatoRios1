package com.codedifferently.tankofamerica.user.serviceandrepo;

import com.codedifferently.tankofamerica.user.exceptions.CurrentUserNotLoggedInException;
import com.codedifferently.tankofamerica.user.modelclasses.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepo userRepo;
    private User currentUser = null;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User create(User user){
        return userRepo.save(user);
    }

    public Iterable<User> getAllUsers(){
        return  userRepo.findAll();
    }

    @Override
    public User getById(Long id) {
        Optional<User> optional = userRepo.findById(id);
        User user = optional.get();
        return user;
    }

    @Override
    public Boolean validateUserPassword(User user, String password){
        if(user.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public void setCurrentUser(String username){
        currentUser = getByUsername(username);
    }

    @Override
    public User getCurrentUser(){
        return currentUser;
    }

    @Override
    public void signOutCurrentUser(){
        currentUser = null;
    }

    @Override
    public Boolean exists(String username) {
        Iterable<User> temp = getAllUsers();
        for(User user: temp ){
            if(username.equals(user.getUsername())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isValidName(String name) {
        Integer validLetters = 0;
        for (int i = 0; i < name.length(); i++) {
            if (Character.isAlphabetic(name.charAt(i))) {
                validLetters++;
            }
        }
        if (validLetters == name.length()) {
            return true;
        }
        return false;
    }

    public Boolean isValidPassword(String str){
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$"; ;
        Pattern pattern = Pattern.compile(regex);
        if(str == null){
            return false;
        }
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    @Override
    public Boolean isValidEmail(String str){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    @Override
    public User getByUsername(String username){
        if(exists(username)){
            Iterable<User> temp = getAllUsers();
            for(User user: temp ){
                if(username.equals(user.getUsername())){
                    return user;
                }
            }
        }
        return null;
    }

    public Boolean userIsLoggedIn(User user){
        if(user == null){
            return false;
        }
        return true;
    }

    public String getUsernameFromUser(User user){
        return user.getUsername();
    }

    public void deleteAccount(User userToDelete){
        userRepo.delete(userToDelete);
    }


}
