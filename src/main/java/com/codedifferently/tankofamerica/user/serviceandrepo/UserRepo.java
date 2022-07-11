package com.codedifferently.tankofamerica.user.serviceandrepo;

import com.codedifferently.tankofamerica.user.modelclasses.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    //User findByUsername(String username);

}
