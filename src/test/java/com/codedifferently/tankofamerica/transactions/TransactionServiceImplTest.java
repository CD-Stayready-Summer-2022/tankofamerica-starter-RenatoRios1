package com.codedifferently.tankofamerica.transactions;

import com.codedifferently.tankofamerica.account.modelclasses.Account;
import com.codedifferently.tankofamerica.account.serviceandrepo.AccountServiceImpl;
import com.codedifferently.tankofamerica.transactions.modelclasses.Transaction;
import com.codedifferently.tankofamerica.transactions.serviceandrepo.TransactionServiceImpl;
import com.codedifferently.tankofamerica.user.modelclasses.User;
import com.codedifferently.tankofamerica.user.serviceandrepo.UserRepo;
import com.codedifferently.tankofamerica.user.serviceandrepo.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
@ExtendWith(SpringExtension.class)
public class TransactionServiceImplTest {
    @Autowired
    TransactionServiceImpl transactionServiceImpl;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    AccountServiceImpl accountServiceImpl;

    @MockBean
    private UserRepo userRepo;


    @Test
    public void createTransactionTest(){
        User user = new User("Test", "Person", "test@test.com", "TestTest123");
        user.setId(1L);
        userServiceImpl.create(user);
        userServiceImpl.setCurrentUser("test@test.com");
        User currentUser = userServiceImpl.getCurrentUser();
        BDDMockito.doReturn(Optional.of(currentUser)).when(userRepo).findById(1L);
        accountServiceImpl.createSavingsFromUser(currentUser);
        accountServiceImpl.createCheckingFromUser(currentUser);
        Double amount = 1.0;
        String transactionAccountOrigin = "Checking";
        String description = "Transaction test in Checking";
        Transaction transaction = transactionServiceImpl.createTransaction(user, amount, transactionAccountOrigin, description);
        Assertions.assertNotNull(transaction);
    }

    @Test
    public void getMonthlyTransactionsTest() {


    }

    @Test
    public void getMonthlyTransactionsCountTest(){

    }

    @Test
    public void getTransactionsTest(){

    }

}
