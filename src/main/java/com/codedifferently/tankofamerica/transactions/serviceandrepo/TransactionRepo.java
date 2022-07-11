package com.codedifferently.tankofamerica.transactions.serviceandrepo;

import com.codedifferently.tankofamerica.transactions.modelclasses.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {
}
