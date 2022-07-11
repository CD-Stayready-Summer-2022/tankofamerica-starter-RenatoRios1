package com.codedifferently.tankofamerica.account.serviceandrepo;


import com.codedifferently.tankofamerica.account.modelclasses.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepo extends CrudRepository<Account, Long> {
}
