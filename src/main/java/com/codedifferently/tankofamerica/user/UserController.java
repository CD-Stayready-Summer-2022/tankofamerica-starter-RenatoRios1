package com.codedifferently.tankofamerica.user;

import com.codedifferently.tankofamerica.TankOfAmericaApplication;
import com.codedifferently.tankofamerica.account.serviceandrepo.AccountServiceImpl;
import com.codedifferently.tankofamerica.shellhelper.InputReader;
import com.codedifferently.tankofamerica.shellhelper.ShellHelper;
import com.codedifferently.tankofamerica.user.exceptions.CurrentUserNotLoggedInException;
import com.codedifferently.tankofamerica.user.modelclasses.CurrentUser;
import com.codedifferently.tankofamerica.user.modelclasses.User;
import com.codedifferently.tankofamerica.user.serviceandrepo.UserService;
import com.codedifferently.tankofamerica.user.serviceandrepo.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@ShellComponent
public class UserController {

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    InputReader inputReader;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @ShellMethod("Create new user login")
    public void createUser() {
        if(userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("Please sign out before attempting to create a new user. ");
            return;
        }
        // 1. read user's email --------------------------------------------
        String email = inputReader.prompt("Email/Username");
        //1. Validate email/username -------------------------------------------------------------------------
        if(!userServiceImpl.isValidEmail(email)){
            shellHelper.printError(String.format("Entered email/username = '%s' is not in valid format --> ABORTING",email));
            return;
        }
        if (userServiceImpl.exists(email)) {
            shellHelper.printError(String.format("User with email='%s' already exist --> ABORTING", email));
            return;
        }
        while (email == null){
            email = inputReader.prompt("First name");
            //1. Validate email/username -------------------------------------------------------------------------
            if(!userServiceImpl.isValidEmail(email)){
                shellHelper.printError(String.format("Entered email/username = '%s' is not in valid format --> ABORTING",email));
                return;
            }
            if (!userServiceImpl.exists(email)) {
                shellHelper.printError(String.format("User with email='%s' does not exist --> ABORTING", email));
                return;
            }
        }
        User user = new User();
        user.setUsername(email);
        // 1. read user's firstName --------------------------------------------
        String firstName = inputReader.prompt("First name");
        if (userServiceImpl.isValidName(firstName)) {
            user.setFirstName(firstName);
        } else {
            shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
        }
        while (user.getFirstName() == null){
            firstName = inputReader.prompt("First name");
            if (StringUtils.hasText(firstName)) {
                user.setFirstName(firstName);
            } else {
                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
            }
        }
        // 2. read user's LastName --------------------------------------------
        String lastName = inputReader.prompt("Last name");
        if (userServiceImpl.isValidName(lastName)) {
            user.setLastName(lastName);
        } else {
            shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
        }
        while (user.getLastName() == null){
            lastName = inputReader.prompt("Last name");
            if (StringUtils.hasText(lastName)) {
                user.setLastName(lastName);
            } else {
                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
            }
        }
        // 3. read user's password --------------------------------------------
        String password = inputReader.prompt("Password", "secret", false);
        if (userServiceImpl.isValidPassword(password)) {
            user.setPassword(password);
        } else {
            shellHelper.printWarning("Password is not valid.");
        }
        while (user.getPassword() == null){
            password = inputReader.prompt("Password", "secret", false);
            if (userServiceImpl.isValidPassword(password)) {
                user.setPassword(password);
            } else {
                shellHelper.printWarning("Please Enter a Valid Value. Try again. ");
            }
        }
        // Print user's input -------------------------------------------------
        shellHelper.printInfo("\nCreating new user:");
        shellHelper.print("\nUsername: " + user.getUsername());
        shellHelper.print("Fullname: " + user.getFullName());
        User createdUser = userServiceImpl.create(user);
        accountServiceImpl.createSavingsFromUser(createdUser);
        accountServiceImpl.createCheckingFromUser(createdUser);
        shellHelper.printInfo("\nPlease sign in with your newly created login.");
    }

    @ShellMethod("Sign in as User")
    public void signIn(){

        if(userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("User already logged in. Please sign out before signing in as another user --> ABORTING");
        }

        // 1. read user's email --------------------------------------------
        String email = inputReader.prompt("Email/Username");
        //1. Validate email/username -------------------------------------------------------------------------
        if(!userServiceImpl.isValidEmail(email)){
            shellHelper.printError(String.format("Entered email/username = '%s' is not in valid format --> ABORTING",email));
            return;
        }
        if (!userServiceImpl.exists(email)) {
            shellHelper.printError(String.format("User with email='%s' does not exist --> ABORTING", email));
            return;
        }
        while (email == null){
            email = inputReader.prompt("First name");
            //1. Validate email/username -------------------------------------------------------------------------
            if(!userServiceImpl.isValidEmail(email)){
                shellHelper.printError(String.format("Entered email/username = '%s' is not in valid format --> ABORTING",email));
                return;
            }
            if (!userServiceImpl.exists(email)) {
                shellHelper.printError(String.format("User with email='%s' does not exist --> ABORTING", email));
                return;
            }
        }

        // 2. read user's Password --------------------------------------------
        User user =userServiceImpl.getByUsername(email);
        String password = inputReader.prompt("Password", "secret", false);
        //2. Validate Password
        if(!userServiceImpl.validateUserPassword(user, password)){
            shellHelper.printError(String.format("Password for user with email='%s' is not valid --> ABORTING", email));
            return;
        }
        while (password == null){
            password = inputReader.prompt("Password", "secret", false);
            //2. Validate Password
            if(!userServiceImpl.validateUserPassword(user, password)){
                shellHelper.printError(String.format("Password for user with email='%s' is not valid --> ABORTING", email));
                return;
            }
        }
        userServiceImpl.setCurrentUser(email);
        shellHelper.printInfo(String.format("LOGGED IN SUCCESSFULLY AS: %S", userServiceImpl.getUsernameFromUser(user)));
    }

    @ShellMethod("log out as user")
    public void logOut(){
        userServiceImpl.signOutCurrentUser();
        shellHelper.print("You have been logged out successfully.");
    }

    @ShellMethod("delete user login data")
    public void deleteUser() {
        User currentUser = userServiceImpl.getCurrentUser();
        if(!userServiceImpl.userIsLoggedIn(currentUser)){
            shellHelper.printInfo("No user is currently signed in. Please sign in.");
            return;
        }

        // 1. read user's email --------------------------------------------
        String email = inputReader.prompt("Email/Username");
        //1. Validate email/username -------------------------------------------------------------------------
        if(!userServiceImpl.isValidEmail(email)){
            shellHelper.printError(String.format("Entered email/username = '%s' is not in valid format --> ABORTING",email));
            return;
        }
        if (!userServiceImpl.exists(email)) {
            shellHelper.printError(String.format("User with email='%s' does not exist --> ABORTING", email));
            return;
        }
        while (email == null){
            email = inputReader.prompt("First name");
            //1. Validate email/username -------------------------------------------------------------------------
            if(!userServiceImpl.isValidEmail(email)){
                shellHelper.printError(String.format("Entered email/username = '%s' is not in valid format --> ABORTING",email));
                return;
            }
            if (!userServiceImpl.exists(email)) {
                shellHelper.printError(String.format("User with email='%s' does not exist --> ABORTING", email));
                return;
            }
        }

        // 2. read user's Password --------------------------------------------
        User user =userServiceImpl.getByUsername(email);
        String password = inputReader.prompt("Password", "secret", false);
        //2. Validate Password
        if(!userServiceImpl.validateUserPassword(currentUser, password) && currentUser.getUsername().equals(email)) {
            shellHelper.printError("User credentials not valid--> ABORTING");
            return;
        }
        String superuserValue = inputReader.promptWithOptions("CONFIRM YOU WOULD LIKE TO DELETE YOUR ACCOUNT. THIS CANNOT BE UNDONE.", "N", Arrays.asList("Y", "N"));
        if ("Y".equals(superuserValue)) {
            accountServiceImpl.deleteChecking(currentUser);
            accountServiceImpl.deleteSavings(currentUser);
            userServiceImpl.deleteAccount(currentUser);
            userServiceImpl.signOutCurrentUser();
        } else {
            return;
        }
        shellHelper.print("Account Deleted");
    }



}
