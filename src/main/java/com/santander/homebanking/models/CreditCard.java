package com.santander.homebanking.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CreditCard extends Card{
    private Long maxLimit = 0L;
    private Long availableLimit = 0L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "creditCard", fetch = FetchType.EAGER)
    Set<TransactionCreditCard> transactions = new HashSet<>();

    public CreditCard(String cardHolder, String number, Integer cvv, LocalDate fromDate,
                      LocalDate thruDate, CardColor color, CardType type, String pin,
                      Long maxLimit, Long availableLimit) {
        super(cardHolder, number, cvv, fromDate, thruDate, color, type, pin);
        this.maxLimit = maxLimit;
        this.availableLimit = availableLimit;
    }

    public CreditCard() {

    }

    public Long getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Long maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Long getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(Long availableLimit) {
        this.availableLimit = availableLimit;
    }

    public Set<TransactionCreditCard> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionCreditCard> transactions) {
        this.transactions = transactions;
    }
}
