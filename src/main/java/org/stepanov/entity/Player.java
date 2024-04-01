package org.stepanov.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Player {
    private String username;
    private String password;
    private BigDecimal balance;
    private List<Transaction> transactions;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = BigDecimal.valueOf(0.0);
        this.transactions = new ArrayList<>();
    }
}