package com.santander.homebanking.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class DebitCard extends Card{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    public DebitCard(String cardHolder, String number, Integer cvv, LocalDate fromDate, LocalDate thruDate, CardColor color, CardType type, String pin) {
        super(cardHolder, number, cvv, fromDate, thruDate, color, type, pin);
    }

    public DebitCard() {
        super();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
