package com.santander.homebanking.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CreditCard extends Card{
    private Double maxLimit = 0.0;
    private Double availableLimit = 0.0;

    @OneToMany(mappedBy = "creditCard", fetch = FetchType.EAGER)
    Set<CreditCardTransaction> creditCardTransactions = new HashSet<>();

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public CreditCard() {
        super();
    }
    public CreditCard(String cardHolder, String number, Integer cvv, LocalDate fromDate, LocalDate thruDate,
                      CardColor color, CardType type, String pin, Double maxLimit, Double availableLimit) {
        super(cardHolder, number, cvv, fromDate, thruDate, color, type, pin);
        this.maxLimit = maxLimit;
        this.availableLimit = availableLimit;
    }


    public Double getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Double maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Double getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(Double availableLimit) {
        this.availableLimit = availableLimit;
    }

    public Set<CreditCardTransaction> getTransactions() {
        return creditCardTransactions;
    }

    public void setTransactions(Set<CreditCardTransaction> creditCardTransactions) {
        this.creditCardTransactions = creditCardTransactions;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
