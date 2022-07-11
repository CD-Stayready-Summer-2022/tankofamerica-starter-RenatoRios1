package com.codedifferently.tankofamerica.account;

import com.codedifferently.tankofamerica.account.modelclasses.Account;
import com.codedifferently.tankofamerica.account.serviceandrepo.AccountService;
import com.codedifferently.tankofamerica.account.serviceandrepo.AccountServiceImpl;
import com.codedifferently.tankofamerica.shellhelper.InputReader;
import com.codedifferently.tankofamerica.shellhelper.ShellHelper;
import com.codedifferently.tankofamerica.transactions.serviceandrepo.TransactionService;
import com.codedifferently.tankofamerica.transactions.serviceandrepo.TransactionServiceImpl;
import com.codedifferently.tankofamerica.user.modelclasses.User;
import com.codedifferently.tankofamerica.user.serviceandrepo.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AccountController {
    @Autowired
    ShellHelper shellHelper;

    @Autowired
    InputReader inputReader;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @Autowired
    TransactionServiceImpl transactionServiceImpl;

    @ShellMethod(value = "View checking account balance",  key="balance-checking")
    public void getCheckingBalance(){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        if(!accountServiceImpl.checkingExists(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No checking accounts found for this user.");
            return;
        }
        Account userAccount = accountServiceImpl.getCheckingAccount(userServiceImpl.getCurrentUser());
        shellHelper.print(String.format("Balance: %.2f", userAccount.getBalance()));
    }

    @ShellMethod(value = "View savings account balance",  key="balance-savings")
    public void getSavingsBalance(){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        if(!accountServiceImpl.savingsExists(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No checking accounts found for this user.");
            return;
        }
        Account userAccount = accountServiceImpl.getSavingsAccount(userServiceImpl.getCurrentUser());
        shellHelper.print(String.format("Balance: %.2f", userAccount.getBalance()));
    }

    @ShellMethod(value = "Add money to checking account", key="deposit-checking")
    public void depositToChecking(@ShellOption({"-DepositAmount", "--deposit"}) String deposit){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        if(!accountServiceImpl.checkingExists(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No checking accounts found for this user.");
            return;
        }

        // 1. read Amount to deposit --------------------------------------------
        Double transferAmount = 0.0;
        if (accountServiceImpl.isDoubleValid(deposit)) {
            shellHelper.printWarning(deposit);
            transferAmount = Double.parseDouble(deposit);
            accountServiceImpl.depositChecking(userServiceImpl.getCurrentUser(), transferAmount);
            transactionServiceImpl.createTransaction(userServiceImpl.getCurrentUser(), transferAmount, "Checking", "Deposit to Checking account.");

        } else {
            shellHelper.printWarning("Please Enter a valid Deposit Qty.");
        }
        shellHelper.printInfo(String.format("Your deposit of %.2f has been added to your checking account.", transferAmount));
    }

    @ShellMethod(value = "Withdraw money from checking account", key = "withdraw-checking")
    public void withdrawFromChecking(@ShellOption({"-WithdrawAmount", "--withdraw"}) String withdraw){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        if(!accountServiceImpl.checkingExists(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No checking accounts found for this user.");
            return;
        }
        // 1. read Amount to deposit --------------------------------------------
        Double transferAmount = 0.0;
        if (accountServiceImpl.isDoubleValid(withdraw)) {
            shellHelper.printWarning(withdraw);
            transferAmount = Double.parseDouble(withdraw);
            String mss = String.format("Double = %.2f", transferAmount);
            shellHelper.printWarning(mss);
            if(transferAmount > accountServiceImpl.getCheckingAccount(userServiceImpl.getCurrentUser()).getBalance()){
                shellHelper.printError("Insufficient funds in checking account to transfer that much quantity");
                return;
            }
            accountServiceImpl.withdrawChecking(userServiceImpl.getCurrentUser(), transferAmount);
            transactionServiceImpl.createTransaction(userServiceImpl.getCurrentUser(), -1*transferAmount, "Checking", "Withdraw from Checking account.");

        } else {
            shellHelper.printWarning("Please Enter a valid Deposit Qty.");
        }
        shellHelper.printInfo(String.format("Your withdraw of %.2f has been updated to your checking account.", transferAmount));
    }


    @ShellMethod(value = "Add money to savings account", key="deposit-savings")
    public void depositToSavings(@ShellOption({"-DepositAmount", "--deposit"}) String deposit){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        if(!accountServiceImpl.checkingExists(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No savings accounts found for this user.");
            return;
        }

        // 1. read Amount to deposit --------------------------------------------
        Double transferAmount = 0.0;
        if (accountServiceImpl.isDoubleValid(deposit)) {
            shellHelper.printWarning(deposit);
            transferAmount = Double.parseDouble(deposit);
            accountServiceImpl.depositChecking(userServiceImpl.getCurrentUser(), transferAmount);
            transactionServiceImpl.createTransaction(userServiceImpl.getCurrentUser(), transferAmount, "Savings", "Deposit to Savings account.");

        } else {
            shellHelper.printWarning("Please Enter a valid Deposit Qty.");
        }
        shellHelper.printInfo(String.format("Your deposit of %.2f has been added to your savings account.", transferAmount));
    }

    @ShellMethod(value = "Withdraw money from savings account", key = "withdraw-savings")
    public void withdrawFromSavings(@ShellOption({"-WithdrawAmount", "--withdraw"}) String withdraw){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        if(!accountServiceImpl.checkingExists(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No savings accounts found for this user.");
            return;
        }
        // 1. read Amount to deposit --------------------------------------------
        Double transferAmount = 0.0;
        if (accountServiceImpl.isDoubleValid(withdraw)) {
            shellHelper.printWarning(withdraw);
            transferAmount = Double.parseDouble(withdraw);
            String mss = String.format("Double = %.2f", transferAmount);
            shellHelper.printWarning(mss);
            if(transferAmount > accountServiceImpl.getCheckingAccount(userServiceImpl.getCurrentUser()).getBalance()){
                shellHelper.printError("Insufficient funds in checking account to transfer that much quantity");
                return;
            }
            accountServiceImpl.withdrawChecking(userServiceImpl.getCurrentUser(), transferAmount);
            transactionServiceImpl.createTransaction(userServiceImpl.getCurrentUser(), -1*transferAmount, "Savings", "Withdraw from Savings account.");

        } else {
            shellHelper.printWarning("Please Enter a valid Deposit Qty.");
        }
        shellHelper.printInfo(String.format("Your withdraw of %.2f has been updated to your savings account.", transferAmount));
    }


}
