package com.santander.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class InterestRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    Integer feeNumber;

    Double interestRate;

    public InterestRate(Integer feeNumber, Double interestRate) {
        this.feeNumber = feeNumber;
        this.interestRate = interestRate;
    }

    public InterestRate() {
    }

    public Long getId() {
        return id;
    }

    public Integer getFeeNumber() {
        return feeNumber;
    }

    public Double getInterestRate() {
        return interestRate;
    }
}
