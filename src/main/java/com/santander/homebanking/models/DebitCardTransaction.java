package com.santander.homebanking.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class DebitCardTransaction extends CardTransaction{

    private Account account;

    public DebitCardTransaction(Double amount, String description, LocalDateTime time, Account account) {
        super(amount, description, time);
        this.account = account;
    }

    public DebitCardTransaction() {
    }

    public Account getAccount() {
        return account;
    }
}
