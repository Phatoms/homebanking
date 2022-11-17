package com.santander.homebanking.dtos;

import javax.validation.constraints.Min;

public class FeesDTO {

    Double amount;

    Integer[] payments;

    public FeesDTO(Double amount, Integer[] payments) {
        this.amount = amount;
        this.payments = payments;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer[] getPayments() {
        return payments;
    }
}
