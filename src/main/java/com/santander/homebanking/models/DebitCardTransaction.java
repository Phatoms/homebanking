package com.santander.homebanking.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class DebitCardTransaction extends CardTransaction{


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    public DebitCardTransaction(Double amount, String description, LocalDateTime time, String token, Status status, Account account) {
        super(amount, description, time, token, status);
        this.account = account;
    }

    public DebitCardTransaction() {
    }

    public Account getAccount() {
        return account;
    }

    public void addAccount(Account account){
        this.account = account;
    }
}
