package com.codedifferently.tankofamerica.transactions;

import com.codedifferently.tankofamerica.account.modelclasses.Account;
import com.codedifferently.tankofamerica.account.serviceandrepo.AccountServiceImpl;
import com.codedifferently.tankofamerica.shellhelper.InputReader;
import com.codedifferently.tankofamerica.shellhelper.ShellHelper;
import com.codedifferently.tankofamerica.transactions.modelclasses.Transaction;
import com.codedifferently.tankofamerica.transactions.serviceandrepo.TransactionService;
import com.codedifferently.tankofamerica.transactions.serviceandrepo.TransactionServiceImpl;
import com.codedifferently.tankofamerica.user.modelclasses.User;
import com.codedifferently.tankofamerica.user.serviceandrepo.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class TransactionController {

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

    @ShellMethod(value =  "View Monthly Statement of CHECKING account", key = "statement-checking")
    public void viewStatementChecking(){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        if(transactionServiceImpl.getMonthlyTransactionsCount(userServiceImpl.getCurrentUser(), "Checking") == 0){
            shellHelper.printInfo("No monthly transactions yet.");
            return;
        }
        shellHelper.print(transactionServiceImpl.getMonthlyTransactions(userServiceImpl.getCurrentUser(), "Checking"));

        shellHelper.print("END OF STATEMENT");
    }

    @ShellMethod(value =  "View Monthly Statement of SAVINGS account", key = "statement-saving")
    public void viewStatementSavings(){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        if(transactionServiceImpl.getMonthlyTransactionsCount(userServiceImpl.getCurrentUser(), "Savings") == 0){
            shellHelper.printInfo("No monthly transactions yet.");
            return;
        }
        shellHelper.print(transactionServiceImpl.getMonthlyTransactions(userServiceImpl.getCurrentUser(), "Savings"));

        shellHelper.print("END OF STATEMENT");
    }

    @ShellMethod(value = "Transfer from CHECKING-->SAVINGS", key = "transfer-c-s")
    public void transferCheckingToSavings(@ShellOption({"-TransferAmount", "--amount"}) String amount){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        Double transferAmount = 0.0;
        if (accountServiceImpl.isDoubleValid(amount)) {
            shellHelper.printWarning(amount);
            transferAmount = Double.parseDouble(amount);
            if(transferAmount > accountServiceImpl.getCheckingAccount(userServiceImpl.getCurrentUser()).getBalance()){
                shellHelper.printError("Insufficient funds in checking account to transfer that much quantity");
                return;
            }
            accountServiceImpl.withdrawChecking(userServiceImpl.getCurrentUser(), transferAmount);
            transactionServiceImpl.createTransaction(userServiceImpl.getCurrentUser(), -1*transferAmount, "Checking", "Transferring from Checking account to Savings account.");
            accountServiceImpl.depositSavings(userServiceImpl.getCurrentUser(), transferAmount);
            transactionServiceImpl.createTransaction(userServiceImpl.getCurrentUser(), transferAmount, "Savings", "Transferring from Checking account to Savings account.");
        } else {
            shellHelper.printWarning("Please Enter a valid Deposit Qty.");
        }
    }

    @ShellMethod(value = "Transfer from SAVINGS-->CHECKING", key = "transfer-s-c")
    public void transferSavignsToChecking(@ShellOption({"-TransferAmount", "--amount"}) String amount){
        if(!userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            shellHelper.printError("No user is currently signed in. Please sign in.");
            return;
        }
        Double transferAmount = 0.0;
        if (accountServiceImpl.isDoubleValid(amount)) {
            shellHelper.printWarning(amount);
            transferAmount = Double.parseDouble(amount);
            if(transferAmount > accountServiceImpl.getSavingsAccount(userServiceImpl.getCurrentUser()).getBalance()){
                shellHelper.printError("Insufficient funds in savings account to transfer desired amount");
                return;
            }
            accountServiceImpl.withdrawSavings(userServiceImpl.getCurrentUser(), transferAmount);
            transactionServiceImpl.createTransaction(userServiceImpl.getCurrentUser(), -1*transferAmount, "Savings", "Transferring from Savings account to Checking account.");
            accountServiceImpl.depositChecking(userServiceImpl.getCurrentUser(), transferAmount);
            transactionServiceImpl.createTransaction(userServiceImpl.getCurrentUser(), transferAmount, "Checking", "Transferring from Savings account to Checking account.");
        } else {
            shellHelper.printWarning("Please Enter a valid Deposit Qty.");
        }
    }



}
