package com.codedifferently.tankofamerica.transactions.modelclasses;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    private LocalDate date;
    private Double amount;
    private Long ownerId;
    private String transactionType;
    private String description;
    public Transaction() {
    }

    public Transaction( Long ownerId, Double amount,  String transactionType, String description) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.ownerId = ownerId;
        this.description = description;
        this.date = LocalDate.from(LocalDate.now());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "date=" + date +
                ", amount=" + String.format("%.2f", amount) +
                ", transactionType='" + transactionType + '\'' +
                ", description='" + description;
    }
}
