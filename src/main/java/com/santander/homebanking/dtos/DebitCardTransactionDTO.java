package com.santander.homebanking.dtos;

import com.santander.homebanking.models.DebitCardTransaction;
import com.santander.homebanking.models.Status;

import java.time.LocalDateTime;

public class DebitCardTransactionDTO {

    private Long id;

    private Double amount;

    private String description;

    private LocalDateTime time;

    private String token;

    private Status status;

    public DebitCardTransactionDTO(DebitCardTransaction debitCardTransaction) {
        this.id = debitCardTransaction.getId();
        this.amount = debitCardTransaction.getAmount();
        this.description = debitCardTransaction.getDescription();
        this.time = debitCardTransaction.getTime();
        this.token = debitCardTransaction.getToken();
        this.status = debitCardTransaction.getStatus();
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

    public Status getStatus() {
        return status;
    }


}
