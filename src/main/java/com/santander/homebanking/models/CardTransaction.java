package com.santander.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class CardTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private Double amount;

    private String description;

    private LocalDateTime time;

    public CardTransaction(Double amount, String description, LocalDateTime time) {
        this.amount = amount;
        this.description = description;
        this.time = time;
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

/*    public PreTransaction getPreTransaction() {
        return preTransaction;
    }*/
}
