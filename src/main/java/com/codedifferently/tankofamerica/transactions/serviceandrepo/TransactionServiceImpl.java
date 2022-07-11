package com.codedifferently.tankofamerica.transactions.serviceandrepo;

import com.codedifferently.tankofamerica.account.serviceandrepo.AccountService;
import com.codedifferently.tankofamerica.transactions.modelclasses.Transaction;
import com.codedifferently.tankofamerica.user.modelclasses.User;
import com.codedifferently.tankofamerica.user.serviceandrepo.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.stream.StreamSupport;

@Service
public class TransactionServiceImpl implements TransactionService{
    private static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private TransactionRepo transactionRepo;
    private UserService userService;
    private AccountService accountService;

    @Autowired
    public TransactionServiceImpl(TransactionRepo transactionRepo, UserService userService, AccountService accountService){
        this.transactionRepo = transactionRepo;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public Transaction createTransaction(User currentUser, Double amount, String transactionAccountOrigin, String description) {
        Transaction transaction = new Transaction(currentUser.getId(), amount, transactionAccountOrigin, description);
        transactionRepo.save(transaction);
        return transaction;
    }

    @Override
    public String getMonthlyTransactions(User user, String accountType) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterable<Transaction> transactions = getTransactions();
        for(Transaction transaction: transactions){
            if(transaction.getTransactionType().equals(accountType) && user.getId() == transaction.getOwnerId()){
                stringBuilder.append(transaction.toString());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public Integer getMonthlyTransactionsCount(User user, String accountType) {
        int count = 0;
        Iterable<Transaction> transactions = getTransactions();
        for(Transaction transaction: transactions){
            if(accountType.equals(transaction.getTransactionType())&& user.getId() == transaction.getOwnerId()){
                count++;
            }
        }
        return count;
    }

    @Override
    public Iterable<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }

    public Integer getTransactionNumber(User user) {
        Iterable<Transaction> userTransactions = getTransactions();
        Iterable<Transaction> newIterable = () -> (Iterator<Transaction>) userTransactions;
        long count = StreamSupport.stream(newIterable.spliterator(), false).count();
        return Math.toIntExact(count);
    }
}
