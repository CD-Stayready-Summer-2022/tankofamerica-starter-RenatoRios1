package com.codedifferently.tankofamerica.shellhelper;

import com.codedifferently.tankofamerica.account.modelclasses.Account;
import com.codedifferently.tankofamerica.user.modelclasses.CurrentUser;
import com.codedifferently.tankofamerica.user.modelclasses.User;
import com.codedifferently.tankofamerica.account.serviceandrepo.AccountServiceImpl;
import com.codedifferently.tankofamerica.user.serviceandrepo.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

@ShellComponent
public class UserCommand {

    private User currentUser = null;

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    InputReader inputReader;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    AccountServiceImpl accountServiceImpl;
//
//    @ShellMethod("create-user")
//    public void createUser(@ShellOption({"-Email", "--email"}) String email) {
//        User user = new User();
//        if(!userServiceImpl.isValidEmail(email)){
//            shellHelper.printError(String.format("Entered email/username = '%s' is not in valid format --> ABORTING",email));
//            return;
//        }
//        if (userServiceImpl.exists(email)) {
//            shellHelper.printError(String.format("User with email='%s' already exists --> ABORTING", email));
//            return;
//        }
//        user.setUsername(email);
//        // 1. read user's firstName --------------------------------------------
//        String firstName = inputReader.prompt("First name");
//        if (userServiceImpl.isValidName(firstName)) {
//            user.setFirstName(firstName);
//        } else {
//            shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
//        }
//        while (user.getFirstName() == null){
//            firstName = inputReader.prompt("First name");
//            if (StringUtils.hasText(firstName)) {
//                user.setFirstName(firstName);
//            } else {
//                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
//            }
//        }
//        // 2. read user's LastName --------------------------------------------
//        String lastName = inputReader.prompt("Last name");
//        if (userServiceImpl.isValidName(lastName)) {
//            user.setLastName(lastName);
//        } else {
//            shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
//        }
//        while (user.getLastName() == null){
//            lastName = inputReader.prompt("Last name");
//            if (StringUtils.hasText(lastName)) {
//                user.setLastName(lastName);
//            } else {
//                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
//            }
//        }
//        // 3. read user's password --------------------------------------------
//        String password = inputReader.prompt("Password", "secret", false);
//        if (userServiceImpl.isValidPassword(password)) {
//            user.setPassword(password);
//        } else {
//            shellHelper.printWarning("Password Cannot be empty string !");
//        }
//        while (user.getPassword() == null){
//            password = inputReader.prompt("Password", "secret", false);
//            if (userServiceImpl.isValidPassword(password)) {
//                user.setPassword(password);
//            } else {
//                shellHelper.printWarning("Please Enter a Valid Value. Try again. ");
//            }
//        }
//        // Print user's input -------------------------------------------------
//        shellHelper.printInfo("\nCreating new user:");
//        shellHelper.print("\nUsername: " + user.getUsername());
//        shellHelper.print("Password: " + user.getPassword());
//        shellHelper.print("Fullname: " + user.getFullName());
//        User createdUser = userServiceImpl.create(user);
//        shellHelper.printSuccess("Created user with id=" + createdUser.getId());
//    }
//
//    @ShellMethod("sign-in")
//    public void signIn(@ShellOption({"-Email", "--email"}) String email, @ShellOption({"-Password", "--password"}) String password){
//        if(userServiceImpl.userIsLoggedIn(currentUser)){
//           shellHelper.printError("Please Sign Out before trying to log in.");
//           return;
//        }
//        User user = new User();
//        //1. Validate email/username -------------------------------------------------------------------------
//        if(!userServiceImpl.isValidEmail(email)){
//            shellHelper.printError(String.format("Entered email/username = '%s' is not in valid format --> ABORTING",email));
//            return;
//        }
//        if (!userServiceImpl.exists(email)) {
//            shellHelper.printError(String.format("User with email='%s' does not exist --> ABORTING", email));
//            return;
//        }
//        user = userServiceImpl.getByUsername(email);
//        //2. Validate Password
//        if(!userServiceImpl.validateUserPassword(user, password)){
//            shellHelper.printError(String.format("Password for user with email='%s' is not valid --> ABORTING", email));
//            return;
//        }
//        shellHelper.printInfo(String.format("LOGGED IN SUCCESSFULLY AS: %S", userServiceImpl.getUsernameFromUser(user)));
//        currentUser = (new CurrentUser(user)).getCurrentUser();
//    }
//
//    @ShellMethod("check-current-user")
//    public void currentUser(){
//        if(!userServiceImpl.userIsLoggedIn(currentUser)){
//            shellHelper.printInfo("No user is currently signed in. Please sign in.");
//            return;
//        }
//        shellHelper.printInfo(String.format("LOGGED IN AS: %S", userServiceImpl.getUsernameFromUser(currentUser)));
//    }
//
//    @ShellMethod("log-out")
//    public void logOut(){
//        currentUser = null;
//        shellHelper.print("You have been logged out successfully.");
//    }
//
//    @ShellMethod("delete-user")
//    public void deleteUser(){
//        if(!userServiceImpl.userIsLoggedIn(currentUser)){
//            shellHelper.printInfo("No user is currently signed in. Please sign in.");
//            return;
//        }
//        userServiceImpl.deleteAccount(currentUser);
//        currentUser = null;
//    }
//
//    @ShellMethod("create-account")
//    public void createCheckingAccount(){
//        if(!userServiceImpl.userIsLoggedIn(currentUser)){
//            shellHelper.printInfo("No user is currently signed in. Please sign in.");
//            return;
//        }
//        accountServiceImpl.createCheckingFromUser(currentUser);
//        shellHelper.print("Account Successfully created");
//    }
//
//
//    @ShellMethod("delete-checking-account")
//    public void deleteCheckingAccount(){
//        if(!userServiceImpl.userIsLoggedIn(currentUser)){
//            shellHelper.printError("No user is currently signed in. Please sign in.");
//            return;
//        }
//        if(!accountServiceImpl.checkingExists(currentUser)){
//            shellHelper.printError("No checking accounts found for this user.");
//            return;
//        }
//        accountServiceImpl.deleteChecking(currentUser);
//        shellHelper.print("Account has been deleted");
//    }
//
//    @ShellMethod("get-account-balance")
//    public void getAccountBalance(){
//        if(!userServiceImpl.userIsLoggedIn(currentUser)){
//            shellHelper.printError("No user is currently signed in. Please sign in.");
//            return;
//        }
//        if(!accountServiceImpl.checkingExists(currentUser)){
//            shellHelper.printError("No checking accounts found for this user.");
//            return;
//        }
//        Account userAccount = accountServiceImpl.getCheckingAccount(currentUser);
//        shellHelper.print(String.format("Balance: %f", userAccount.getBalance()));
//    }
//
//    @ShellMethod("Add money to checking account")
//    public void depositToChecking(@ShellOption({"-DepositAmount", "--deposit"}) String deposit){
//        Double temp = 0.0;
//        if(!userServiceImpl.userIsLoggedIn(currentUser)){
//            shellHelper.printError("No user is currently signed in. Please sign in.");
//            return;
//        }
//        if(!accountServiceImpl.checkingExists(currentUser)){
//            shellHelper.printError("No checking accounts found for this user.");
//            return;
//        }
//        // 1. read Amount to deposit --------------------------------------------
//        if (accountServiceImpl.isDoubleValid(deposit)) {
//            shellHelper.printWarning(deposit);
//            temp = Double.parseDouble(deposit);
//            String mss = String.format("Double = %.2f", temp);
//            shellHelper.printWarning(mss);
//            accountServiceImpl.depositChecking(currentUser, temp);
//        } else {
//            shellHelper.printWarning("Please Enter a valid Deposit Qty.");
//        }
//        shellHelper.printInfo(String.format("Your deposit of %.2f has been added to your checking account.", temp));
//    }
//
//    @ShellMethod("Withdraw money from checking account")
//    public void withdrawFromChecking(@ShellOption({"-WithdrawAmount", "--withdraw"}) String withdraw){
//        Double temp = 0.0;
//        if(!userServiceImpl.userIsLoggedIn(currentUser)){
//            shellHelper.printError("No user is currently signed in. Please sign in.");
//            return;
//        }
//        if(!accountServiceImpl.checkingExists(currentUser)){
//            shellHelper.printError("No checking accounts found for this user.");
//            return;
//        }
//        // 1. read Amount to deposit --------------------------------------------
//        if (accountServiceImpl.isDoubleValid(withdraw)) {
//            shellHelper.printWarning(withdraw);
//            temp = Double.parseDouble(withdraw);
//            String mss = String.format("Double = %.2f", temp);
//            shellHelper.printWarning(mss);
//            accountServiceImpl.withdrawChecking(currentUser, temp);
//        } else {
//            shellHelper.printWarning("Please Enter a valid Deposit Qty.");
//        }
//        shellHelper.printInfo(String.format("Your withdraw of %.2f has been updated to your checking account.", temp));
//    }
//




}
