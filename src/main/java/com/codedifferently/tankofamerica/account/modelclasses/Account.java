package com.codedifferently.tankofamerica.account.modelclasses;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name= "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    private String accountType;
    private Double balance;
    private Long ownerId;


    public Account() {
    }

    public Account(Long ownerId, Double balance, String accountType) {
        this.balance = balance;
        this.ownerId = ownerId;
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void add(Double amount){
        balance += amount;
    }

    public void subtract(Double amount){
        balance -= amount;
    }

    public UUID getId() {
        return id;
    }
}
