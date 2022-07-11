package com.codedifferently.tankofamerica.account.serviceandrepo;

import com.codedifferently.tankofamerica.account.modelclasses.Account;
import com.codedifferently.tankofamerica.user.exceptions.CurrentUserNotLoggedInException;
import com.codedifferently.tankofamerica.user.modelclasses.User;
import com.codedifferently.tankofamerica.user.serviceandrepo.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private AccountRepo accountRepo;

    private UserService userService;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo, UserService userService){
        this.userService = userService;
        this.accountRepo = accountRepo;
    }

    @Override
    public Account createCheckingFromUser(User currentUser){
        Account account = new Account(currentUser.getId(), 0.0 , "Checking");
        return accountRepo.save(account);
    }

    @Override
    public Account createSavingsFromUser(User currentUser){
        Account account = new Account(currentUser.getId(), 0.0 ,"Savings");
        return accountRepo.save(account);
    }

    @Override
    public Iterable<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public void deleteChecking(User currentUser) {
        Account accountToDelete = getCheckingAccount(currentUser);
        accountRepo.delete(accountToDelete);
    }

    @Override
    public void deleteSavings(User currentUser) {
        Account accountToDelete = getSavingsAccount(currentUser);
        accountRepo.delete(accountToDelete);
    }

    @Override
    public Account getCheckingAccount(User currentUser) {
        Iterable<Account> temp = getAllAccounts();
        for(Account account: temp){
            if(currentUser.getId() == account.getOwnerId() && account.getAccountType().equals("Checking")){
                return account;
            }
        }
        return null;
    }

    @Override
    public Account getSavingsAccount(User currentUser) {
        Iterable<Account> temp = getAllAccounts();
        for(Account account: temp){
            if(currentUser.getId() == account.getOwnerId() && account.getAccountType().equals("Savings")){
                return account;
            }
        }
        return null;
    }
    @Override
    public Boolean checkingExists(User currentUser) {
        Iterable<Account> temp = getAllAccounts();
        for(Account account: temp){
            if(currentUser.getId() == account.getOwnerId() && account.getAccountType().equals("Checking")){
                return true ;
            }
        }
        return false;
    }

    @Override
    public Boolean savingsExists(User currentUser){
        Iterable<Account> temp = getAllAccounts();
        for(Account account: temp){
            if(currentUser.getId() == account.getOwnerId() && account.getAccountType().equals("Savings")){
                return true ;
            }
        }
        return false;
    }

    @Override
    public void depositChecking(User currentUser, Double amount){
        Account account = getCheckingAccount(currentUser);
        account.add(amount);
        accountRepo.save(account);
    }

    @Override
    public void withdrawChecking(User currentUser, Double amount){
        Account account = getCheckingAccount(currentUser);
        account.subtract(amount);
        accountRepo.save(account);
    }

    @Override
    public void depositSavings(User currentUser, Double amount){
        Account account = getSavingsAccount(currentUser);
        account.add(amount);
        accountRepo.save(account);
    }

    @Override
    public void withdrawSavings(User currentUser, Double amount){
        Account account = getSavingsAccount(currentUser);
        account.subtract(amount);
        accountRepo.save(account);
    }


    public Boolean isDoubleValid(String str){
        if(str.equals("")){
            return false;
        }
        //isMoneyValue - only two decimals are allowed (ex: 1.00 & 3.52 is okay, but 1.023 is not)
        int counter, periodCounter, excessDecimalDigits, offset;
        counter = periodCounter = excessDecimalDigits = 0;
        offset = 2;
        int decimalLocation = 0; //USED TO SEE WHERE THE PERIOD '.' IN A FLOAT IS
        for(int i = 0; i < str.length(); i++){
            if( Character.isDigit(str.charAt(i))) {  //ALLOWED CHARACTERS 0-9 AND '.' IN CASE OF FLOATS
                counter += 1;
            }
            if (str.charAt(i) == '.') {         //IN CASE OF FLOATS, ONLY ONE DECIMAL WOULD BE ALLOWED (1.0 IS OKAY TO ENTER)
                periodCounter += 1;
                decimalLocation = i;
            }
            if (i > (decimalLocation + offset)) {
                if (decimalLocation > 0) {
                    excessDecimalDigits += ((str.charAt(i)) - '0');  //ADDS ANY VALUES TRAILING A '.' AND ADDS THOSE VALUES UP
                }
            }
        }
        //IF STRING LENGTH IS SATISFIED, AND IS A VALID FORMAT
        if (((counter+periodCounter == str.length()) && periodCounter<= 1 && excessDecimalDigits == 0)){
            return true;
        }else{
            return false;
        }
    }



}
