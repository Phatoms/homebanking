package com.santander.homebanking.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CreditCardTransaction extends CardTransaction{
    private Integer payments;

    private Double interestRate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;

    public CreditCardTransaction(Double amount, String description, LocalDateTime time, String token, Status status, Integer payments, Double interestRate, CreditCard creditCard) {
        super(amount, description, time, token, status);
        this.payments = payments;
        this.interestRate = interestRate;
        this.creditCard = creditCard;
    }

    public CreditCardTransaction() {
    }

    public Integer getPayments() {
        return payments;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }
}
