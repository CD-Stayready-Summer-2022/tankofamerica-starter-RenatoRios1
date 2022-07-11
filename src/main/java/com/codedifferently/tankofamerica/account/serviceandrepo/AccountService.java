package com.codedifferently.tankofamerica.account.serviceandrepo;

import com.codedifferently.tankofamerica.account.modelclasses.Account;
import com.codedifferently.tankofamerica.user.modelclasses.User;

public interface AccountService  {
    Account createCheckingFromUser(User currentUser);
    Account createSavingsFromUser(User currentUser);
    Account getCheckingAccount(User currentUser);
    Account getSavingsAccount(User currentUser);
    Boolean checkingExists(User currentUser);
    Boolean savingsExists(User currentUser);
    Iterable<Account> getAllAccounts();
    void deleteChecking(User currentUser);
    void deleteSavings(User currentUser);
    void depositChecking(User currentUser, Double amount);
    void withdrawChecking(User currentUser, Double amount);
    void depositSavings(User currentUser, Double amount);
    void withdrawSavings(User currentUser, Double amount);
}
