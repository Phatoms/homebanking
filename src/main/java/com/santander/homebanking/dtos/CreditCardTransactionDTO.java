package com.santander.homebanking.dtos;

import com.santander.homebanking.models.CreditCard;
import com.santander.homebanking.models.CreditCardTransaction;
import com.santander.homebanking.models.Status;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class CreditCardTransactionDTO {

    private Long id;

    private Double amount;

    private String description;

    private LocalDateTime time;

    private String token;


    private Integer payments;

    private Double interestRate;

    public CreditCardTransactionDTO(CreditCardTransaction creditCardTransaction) {
        this.id = creditCardTransaction.getId();
        this.amount = creditCardTransaction.getAmount();
        this.description = creditCardTransaction.getDescription();
        this.time = creditCardTransaction.getTime();
        this.token = creditCardTransaction.getToken();
        this.payments = creditCardTransaction.getPayments();
        this.interestRate = creditCardTransaction.getInterestRate();
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

    public String getToken() {
        return token;
    }

    public Integer getPayments() {
        return payments;
    }

    public Double getInterestRate() {
        return interestRate;
    }
}
