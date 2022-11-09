package com.santander.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransactionCreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    private Long id;

    private Double amount;

    private String description;

    private LocalDateTime time;

    private Integer payments;

    private Float interestRate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;

    public TransactionCreditCard(Double amount, String description, LocalDateTime time, Integer payments, Float interestRate) {
        this.amount = amount;
        this.description = description;
        this.time = time;
        this.payments = payments;
        this.interestRate = interestRate;
    }

    public TransactionCreditCard() {
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTime() {
        return time;
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

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
