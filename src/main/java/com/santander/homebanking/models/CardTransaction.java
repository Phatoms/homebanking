package com.santander.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class CardTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private Double amount;

    private String description;

    private LocalDateTime time;

    private String token;

    private Status status;

    public CardTransaction(Double amount, String description, LocalDateTime time, String token, Status status) {
        this.amount = amount;
        this.description = description;
        this.time = time;
        this.token = token;
        this.status = status;
    }

    public CardTransaction() {
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

    public void setStatus(Status status) {
        this.status = status;
    }
}
