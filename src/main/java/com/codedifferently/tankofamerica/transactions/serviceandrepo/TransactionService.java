package com.codedifferently.tankofamerica.transactions.serviceandrepo;

import com.codedifferently.tankofamerica.transactions.modelclasses.Transaction;
import com.codedifferently.tankofamerica.user.modelclasses.User;

public interface TransactionService {
    Transaction createTransaction(User currentUser, Double amount, String transactionType, String description);

    String getMonthlyTransactions(User user, String accountType);

    Integer getMonthlyTransactionsCount(User user, String accountType);

    Iterable<Transaction> getTransactions();
}
