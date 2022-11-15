package com.santander.homebanking.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CreditCardTransaction extends CardTransaction{
    private Integer payments;

    private Float interestRate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_card_transaction")
    private CreditCard creditCard;

    public CreditCardTransaction(Double amount, String description, LocalDateTime time, Integer payments, Float interestRate, CreditCard creditCard) {
        super(amount, description, time);
        this.payments = payments;
        this.interestRate = interestRate;
        this.creditCard = creditCard;
    }

    public CreditCardTransaction() {
    }

    public Integer getPayments() {
        return payments;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }
}
